package com.iung.dev.domain.user.service;


import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.enumerate.TokenState;
import com.iung.dev.common.exception.*;
import com.iung.dev.common.utils.CommonUtils;
import com.iung.dev.common.utils.TokenUtils;
import com.iung.dev.domain.user.mapper.UserMapper;
import com.iung.dev.domain.user.model.RegistUserDto;
import com.iung.dev.domain.user.model.SearchUserDto;
import com.iung.dev.domain.user.model.ModifyPwdDto;
import com.iung.dev.domain.user.model.ModifyUserDto;
import com.iung.dev.domain.user.model.UserDto;
import com.iung.dev.domain.user.model.UserIdCheckDto;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final CommonUtils commonUtils;

    /**
     * 아이디 중복 체크
     */
    private int checkMemberIdExists(String memberId) {
        return userMapper.checkMemberIdExists(memberId);
    }

    public Map<String, Boolean> checkIdDuplicate(UserIdCheckDto userIdCheckDto) {
        // memberIdValidation
        CommonUtils.memberIdValidation(userIdCheckDto.getMemberId());
        boolean isDuplicate= checkMemberIdExists(userIdCheckDto.getMemberId()) > 0;
        Map result = new HashMap<>();
        result.put("isDuplicate", isDuplicate);
        return result;
    }
    /**
     * find 단건
     */
    public UserDto findMe(Cookie cookie) {
//        if (cookie == null) {
//            throw new AuthenticationException("필수 정보를 찾을 수 없습니다.");
//        }
//        String token = cookie.getValue();
//        if (token == null) {
//            throw new AuthenticationException("토큰 정보를 찾을 수 없습니다.");
//        }
//        TokenState tokenState = TokenUtils.getTokenState(token);
//        if (tokenState != TokenState.NORMAL) {
//            throw new AuthenticationException("토큰이 유효하지 않습니다.");
//        }
        String token = cookie.getValue();
        String memberId = TokenUtils.getUserLoginIdFromToken(token);
        return findUser(memberId);
    }

    public UserDto findUser(String memberId)  {
        UserDto userDto = userMapper.findById(memberId).orElseThrow(() -> new DataNotFoundException("Member 정보를 찾을 수 없습니다."));
        return userDto;
    }

    /**
     * 회원가입
     */
    @Transactional
    public UserDto register(RegistUserDto userDto) {
        // memberRegistDtoValidation
        Boolean isValid = CommonUtils.memberRegistDtoValidation(userDto);
        if (!isValid) {
            return null;
        }
        boolean isDuplicate= checkMemberIdExists(userDto.getMemberId()) > 0;
        if (isDuplicate) {
            throw new DuplicateDataException("이미 존재하는 아이디 입니다");
        }

        // 암호화
        String pwd = CommonUtils.getPattenPassword(userDto.getMemberPw());
        userDto.setMemberPw(pwd);

        userMapper.register(userDto);
        UserDto newUserDto = userMapper.findById(userDto.getMemberId()).orElseThrow(() -> new DataNotFoundException("Member 등록에 실패했습니다."));
        return newUserDto;
    }


    public Page<UserDto> search(SearchUserDto searchUserDto) {
        List<UserDto> userDtoList= userMapper.searchList(searchUserDto, searchUserDto.getPageRequest());
        int total = userMapper.searchCount(searchUserDto);
        return new PageImpl<>(userDtoList, searchUserDto.getPageRequest(), total);
    }


    /**
     * 회원 정보 수정
     */

    @Transactional
    public UserDto updateMe(Cookie cookie, ModifyUserDto modifyUserDto) {
        String token = cookie.getValue();
        String memberId = TokenUtils.getUserLoginIdFromToken(token);
        return this.update(memberId, modifyUserDto);
    }



    @Transactional
    public UserDto update(String memberId, ModifyUserDto modifyUserDto) {
        CommonUtils.memberModifyDtoValidation(modifyUserDto);
        userMapper.findById(memberId).orElseThrow(() -> new DataNotFoundException("Member 정보를 찾을 수 없습니다."));
        userMapper.update(memberId, modifyUserDto);
        return this.findUser(memberId);
    }

//
    @Transactional
    public UserDto updateMePassword(Cookie cookie, ModifyPwdDto modifyPwdDto) {
        String token = cookie.getValue();
        String memberId = TokenUtils.getUserLoginIdFromToken(token);
        userMapper.findById(memberId).orElseThrow(() -> new DataNotFoundException("Member 정보를 찾을 수 없습니다."));
        CommonUtils.memberModifyPwdDtoValidation(modifyPwdDto);

        String currentPatPwd = userMapper.findPwd(memberId);
        Boolean match = CommonUtils.matchPassword(modifyPwdDto.getMemberPw(), currentPatPwd);

        if (!match) {
            throw new BadRequestException("현재 비밀번호가 일치하지 않습니다.");
        }
        String newPassword = CommonUtils.getPattenPassword(modifyPwdDto.getMemberNewPw());
        int result = userMapper.updatePwd(memberId, newPassword);
        if (result > 0) {
            return this.findUser(memberId);
        }
        throw new UnknownServerException("비밀번호 변경에 실패했습니다.");
    }
}
