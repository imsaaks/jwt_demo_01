package com.iung.dev.common.interceptor;

import com.iung.dev.common.utils.TokenUtils;
import com.iung.dev.common.exception.code.GlobalErrorCodes;
import com.iung.dev.common.exception.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String requestURI = request.getRequestURI();
        if (isStaticResource(requestURI)) {
            return true;
        }
        String token = TokenUtils.getTokenFromCookie(request);
        if (!StringUtils.isBlank(token)) {
            if (TokenUtils.validateToken(token)) {
                String userId = TokenUtils.getUserLoginIdFromToken(token);
                if (userId == null || userId.isEmpty()) {
                    throw new AuthenticationException(GlobalErrorCodes.AUTH_ERROR_CODE, "인증 토큰에 필수정보가 없습니다.");
                }
                return true;
            } else {
                throw new AuthenticationException(GlobalErrorCodes.INVALID_TOKEN_CODE, "인증 토큰이 유효하지 않습니다.");
            }
        } else {
            throw new AuthenticationException(GlobalErrorCodes.AUTH_ERROR_CODE, "인증 토큰이 필요합니다.");
        }
    }

    private boolean isStaticResource(String requestURI) {
        String[] staticExtensions = {".js", ".css", ".jpg", ".gif", ".png", ".ico", ".html", "map"};
        return Arrays.stream(staticExtensions).anyMatch(requestURI::endsWith);
    }
}
