package com.iung.dev.domain.user.service;


import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.utils.CommonUtils;
import com.iung.dev.domain.user.mapper.UserMapper;
import com.iung.dev.domain.user.model.RegistUserDto;
import com.iung.dev.domain.user.model.SearchUserDto;
import com.iung.dev.domain.user.model.ModifyUserDto;
import com.iung.dev.domain.user.model.UserDto;
import com.iung.dev.domain.user.model.UserIdCheckDto;

import com.iung.dev.common.exception.BadRequestException;
import com.iung.dev.common.exception.DuplicateDataException;
import com.iung.dev.common.exception.DataNotFoundException;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

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
            throw new DuplicateDataException("이미 존재하는 Member Id 입니다");
        }
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
    public UserDto update(String memberId, ModifyUserDto modifyUserDto) {
        CommonUtils.memberModifyDtoValidation(modifyUserDto);
        userMapper.findById(memberId).orElseThrow(() -> new DataNotFoundException("Member 정보를 찾을 수 없습니다."));
        userMapper.update(memberId, modifyUserDto);
        return this.findUser(memberId);
    }

//
//    @Transactional
//    public UserDto update(ModifyUserDto modifyUserDto) {
//        CommonUtils.memberModifyDtoValidation(modifyUserDto);
//
//        userMapper.findById(modifyUserDto.getMemberId()).orElseThrow(() -> new DataNotFoundException("Member 정보를 찾을 수 없습니다."));
//        userMapper.update(modifyUserDto);
//        return this.findUser(modifyUserDto.getMemberId());
//    }


    public int incrementFailedCount(String memberId) {
        return userMapper.incrementFailedCount(memberId);
    }

    public UserDto updatePwd(String memberId, UserDto userDto) {
        return null;
    }
}
