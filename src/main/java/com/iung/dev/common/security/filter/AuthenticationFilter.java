package com.iung.dev.common.security.filter;

import com.iung.dev.common.exception.code.GlobalErrorCodes;
import com.iung.dev.common.exception.UnknownServerException;
import com.iung.dev.common.model.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        AuthenticationToken authRequest;
        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (Exception e) {
            throw new UnknownServerException(e);
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private AuthenticationToken getAuthRequest(HttpServletRequest request) {
        try {
            if("POST".equals(request.getMethod())){
                // ObjectMapper objectMapper = GlobalObjectMapper.getDefaultMapper();
                ObjectMapper objectMapper = new ObjectMapper();
                LoginDto login = objectMapper.readValue(request.getInputStream(), LoginDto.class);
                log.debug("1.CustomAuthenticationFilter :: loginId: {}, userPw: {}", login.getMemberId(), login.getMemberPw());
                return new AuthenticationToken(login.getMemberId(), login.getMemberPw());
            }else{
                throw new com.iung.dev.common.exception.AuthenticationException(GlobalErrorCodes.BAD_REQUEST_CODE, "Authentication method not supported: " + request.getMethod());
            }
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new UnknownServerException(e);
        }
    }
}
