package com.iung.dev.common.security.filter;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.config.SecurityConfig;
import com.iung.dev.common.model.SecurityUserDetailsDto;
import com.iung.dev.common.security.service.AuthUserService;
import com.iung.dev.common.exception.code.GlobalErrorCodes;
import com.iung.dev.common.utils.TokenUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthUserService authUserService;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilterInternal(
            HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        if (shouldExclude(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestUri = request.getRequestURI();

        String token = TokenUtils.getTokenFromCookie(request);
        if (StringUtils.isBlank(token)) {
            if (!AppConstants.SIGN_IN_PATH.equals(requestUri) && !AppConstants.SIGN_UP_PATH.equals(requestUri)
                    && !AppConstants.CHECK_ID_DUPLICATE_PATH.equals(requestUri)  && !AppConstants.SIGN_OUT_PATH.equals(requestUri)) {
                throw new com.iung.dev.common.exception.AuthenticationException(GlobalErrorCodes.AUTH_ERROR_CODE, "인증 토큰이 필요합니다.");
            }
        }
        try {
            validateAndSetAuthentication(token);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }

    }


    private void handleException(HttpServletResponse response, Exception e) {
        // HTTP 401 Unauthorized

        Map<String, Object> errorResponse = jsonResponseWrapper(e);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(new ObjectMapper().writeValueAsString(errorResponse));
            printWriter.flush();
            printWriter.close();
        } catch (IOException ex) {
            log.error("Error writing response", ex);
        }
    }

    private boolean shouldExclude(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            // log.info("shouldExclude result1 true!");
            return true;
        }

        if (SecurityConfig.PUBLIC_PERMIT_URL_ARRAY != null && !SecurityConfig.PUBLIC_PERMIT_URL_ARRAY.isEmpty()){
            for (String permitUri : SecurityConfig.PUBLIC_PERMIT_URL_ARRAY){
                if (pathMatcher.match(permitUri, request.getRequestURI())){
                    return true;
                }
            }
        }
        return false;
    }


    private void validateAndSetAuthentication(String token) {
        log.debug("validateAndSetAuthentication 시작: {}", token); // 로그 추가

        if (TokenUtils.validateToken(token)) {
            String loginId = TokenUtils.getUserLoginIdFromToken(token);

            List<String> authorities = TokenUtils.getAuthoritiesFromToken(token);
            String userName = TokenUtils.getUserNameFromToken(token);
            SecurityUserDetailsDto securityUserDetailsDto = (SecurityUserDetailsDto) authUserService.loadUserById(loginId);
            List<GrantedAuthority> grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            AuthenticationToken authentication =
                    new AuthenticationToken(securityUserDetailsDto, userName, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new com.iung.dev.common.exception.AuthenticationException(GlobalErrorCodes.INVALID_TOKEN_CODE, "유효하지 않은 Token 입니다.");
        }
    }

    private Map<String, Object> jsonResponseWrapper(Exception e) {
        String resultMessage;

        if (e instanceof ExpiredJwtException) {
            resultMessage = "Token 기간 만료";
        } else if (e instanceof SecurityException) {
            resultMessage = "허용된 Token이 아닙니다.";
        } else if (e instanceof JwtException) {
            resultMessage = "Token을 parsing 하던 중 예외가 발생했습니다.";
        } else {
            resultMessage = "Token 에러입니다.";
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("code", "9999");
        jsonMap.put("message", resultMessage);
        jsonMap.put("reason", e.getMessage());
        return jsonMap;
    }

}
