package com.iung.dev.common.utils;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.exception.BadRequestException;
import com.iung.dev.domain.user.model.ModifyPwdDto;
import com.iung.dev.domain.user.model.ModifyUserDto;
import com.iung.dev.domain.user.model.RegistUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Component
public class CommonUtils {

    public static boolean isCamelCase(String str) {
        return str.matches("^[a-z]+([A-Z][a-z0-9]+)*$");
    }
    public static String camelToUpperSnakeCase(String str) {
        if (str == null || str.isEmpty() || !isCamelCase(str)) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append('_');
                result.append(ch);
            } else {
                result.append(Character.toUpperCase(ch));
            }
        }
        return result.toString();
    }


    public static boolean memberIdValidation(String memberId) {
        if (memberId == null || memberId.isEmpty()) {
            throw new BadRequestException("아이디 가 null 또는 빈문자열 입니다.");
        }
        if (!memberId.matches(AppConstants.MEMBER_ID_REGEX)) {
            throw new BadRequestException("아이디 는 알파벳으로 시작하며 5~12자의 알파벳, 숫자, 밑줄(_), @만 가능합니다.");
        }
        return true;
    }


    public static boolean memberRegistDtoValidation(RegistUserDto userDto) {
        if (userDto.getMemberId() == null || userDto.getMemberId().isEmpty()) {
            throw new BadRequestException("아이디 가 null 또는 빈문자열 입니다.");
        }
        if (!userDto.getMemberId().matches(AppConstants.MEMBER_ID_REGEX)) {
            throw new BadRequestException("아이디 는 알파벳으로 시작하며 5~12자의 알파벳, 숫자, 밑줄(_), @만 가능합니다.");
        }
        if (userDto.getMemberPw() == null || userDto.getMemberPw().isEmpty()) {
            throw new BadRequestException("비밀번호 가 null 또는 빈문자열 입니다.");
        }
        if (!userDto.getMemberPw().matches(AppConstants.MEMBER_PW_REGEX)) {
            throw new BadRequestException("비밀번호 는 8~15자 이하의 영문, 숫자, 특수문자(@$!%*#?&)가 포함되어야 합니다.");
        }
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty() ) {
            throw new BadRequestException("이름 이 null 또는 빈문자열 입니다.");
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            if (!userDto.getEmail().matches(AppConstants.EMAIL_REGEX)) {
                throw new BadRequestException("이메일 형식이 올바르지 않습니다.");
            }
        }
        return true;
    }

    public static boolean memberModifyDtoValidation(ModifyUserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty() ) {
            throw new BadRequestException("Member 이름 이 null 또는 빈문자열 입니다.");
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            if (!userDto.getEmail().matches(AppConstants.EMAIL_REGEX)) {
                throw new BadRequestException("Member 이메일 형식이 올바르지 않습니다.");
            }
        }
        return true;
    }


    public static boolean memberModifyPwdDtoValidation(ModifyPwdDto userDto) {
        if (userDto.getMemberPw() == null || userDto.getMemberPw().isEmpty() ) {
            throw new BadRequestException("기존 비밀번호 가 null 또는 빈문자열 입니다.");
        }
        if (userDto.getMemberNewPw() == null || userDto.getMemberNewPw().isEmpty() ) {
            throw new BadRequestException("변경할 비밀번호 가 null 또는 빈문자열 입니다.");
        }
        if (!userDto.getMemberNewPw().equals(userDto.getMemberNewPw()) ) {
            throw new BadRequestException("변경할 비밀번호가 기존 비밀번호와 일치하지 않습니다.");
        }
        if (!userDto.getMemberNewPw().matches(AppConstants.MEMBER_PW_REGEX)) {
            throw new BadRequestException("변경할 비밀번호 는 8~15자 이하의 영문, 숫자, 특수문자(@$!%*#?&)가 포함되어야 합니다.");
        }

        return true;
    }


    // 회원등록 시 패스워드 암호화
    public static String getPattenPassword(String pwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(pwd);
    }
    //  passwordEncoder.matches
    public static boolean matchPassword(String pwd, String encodedPwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(pwd, encodedPwd);
    }
}
