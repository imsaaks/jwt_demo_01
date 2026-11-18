package com.iung.dev.common.security.provider;

import com.iung.dev.common.model.SecurityUserDetailsDto;
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
        log.info("userId: {}, userPassword: {}", userId, userPassword);

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userPassword)) {
            log.info("========A login fail!");
            throw new BadCredentialsException("Invalid username or password");
        }
        SecurityUserDetailsDto securityUserDetailsDto = (SecurityUserDetailsDto) userDetailsService.loadUserByUsername(userId);
        UserDto userDto = securityUserDetailsDto.getUserDto();


        String pswd = userDetailsService.loadUserPwd(userDto.getMemberId());
        // log.info("pswd: {}", pswd);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isMatch = encoder.matches(userPassword, pswd);
        // log.info("isMatch: {}", isMatch);

        if (StringUtils.isBlank(userPassword) || !isMatch) {
            // 로그인 실패시 로그인 실패 카운트 증감
            userDetailsService.incrementFailedCount(securityUserDetailsDto.getUsername());
            log.info("login fail! : {}", securityUserDetailsDto.getUsername() + " Invalid password");
            throw new BadCredentialsException(securityUserDetailsDto.getUsername() + " Invalid password");
        }
        return new AuthenticationToken(securityUserDetailsDto, userPassword, securityUserDetailsDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
