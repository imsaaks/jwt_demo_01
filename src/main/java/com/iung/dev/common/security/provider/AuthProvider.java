package com.iung.dev.common.security.provider;

import com.iung.dev.common.security.model.SecurityUserDto;
import com.iung.dev.common.security.exception.AccessPreventionCodes;
import com.iung.dev.common.security.filter.AuthenticationToken;
import com.iung.dev.domain.user.model.UserDto;
import com.iung.dev.common.security.service.AuthUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final AuthUserService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AuthenticationToken token = (AuthenticationToken) authentication;
        String userId = token.getName();
        String userPassword = (String)token.getCredentials();
        // log.info("userId: {}, userPassword: {}", userId, userPassword);

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userPassword)) {
            throw new BadCredentialsException(AccessPreventionCodes.ID_BLANK_CODE.getCode());
        }
        SecurityUserDto securityUserDto = (SecurityUserDto) userDetailsService.loadUserByUsername(userId);
        UserDto userDto = securityUserDto.getUserDto();

        // 사용여부, 삭제여부, 탈퇴여부, 계정잠김 여부 확인
        String errCode = null;
        if ("N".equals(userDto.getUseYn())) {
            errCode = AccessPreventionCodes.USE_YN_N_CODE.getCode();
        } else if ("Y".equals(userDto.getDeleteYn())) {
            errCode = AccessPreventionCodes.DELETE_YN_Y_CODE.getCode();
        } else if ("Y".equals(userDto.getWithdrawYn())) {
            errCode = AccessPreventionCodes.WITHDRAW_YN_Y_CODE.getCode();
        } else if ("Y".equals(userDto.getLockYn())) {
            errCode = AccessPreventionCodes.LOCK_YN_Y_CODE.getCode();
        }
        if (errCode != null) {
            throw new BadCredentialsException(errCode);
        }

        String pswd = userDetailsService.loadUserPwd(userDto.getMemberId());
        // log.info("pswd: {}", pswd);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isMatch = encoder.matches(userPassword, pswd);
        // log.info("isMatch: {}", isMatch);

        if (StringUtils.isBlank(userPassword) || !isMatch) {
            // 로그인 실패시 로그인 실패 카운트 증감
            userDetailsService.incrementFailedCount(securityUserDto.getUsername());
            throw new BadCredentialsException(AccessPreventionCodes.ID_PWD_NOT_MATCH_CODE.getCode());
        }

        // 로그인 성공시 로그인 실패 카운트 초기화
        userDetailsService.resetFailedCount(securityUserDto.getUsername());
        return new AuthenticationToken(securityUserDto, userPassword, securityUserDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
