package com.iung.dev.common.security.handler;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.exception.DevException;
import com.iung.dev.common.security.exception.AccessPreventionCodes;
import com.iung.dev.common.utils.TokenUtils;
import com.iung.dev.common.security.model.SecurityUserDto;
import com.iung.dev.domain.user.model.UserDto;
import com.iung.dev.domain.user.model.ResponseUserDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDto userDto = ((SecurityUserDto) authentication.getPrincipal()).getUserDto();

            ResponseUserDto responseUserDto = ResponseUserDto.builder()
                    .memberId(userDto.getMemberId())
                    .userName(userDto.getUsername()).build();

            JSONObject userDtoObject = new JSONObject(objectMapper.writeValueAsString(responseUserDto));

            HashMap<String, Object> resultMap = new HashMap<>();
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            resultMap.put("userInfo", userDtoObject);
            resultMap.put("resultCode", AccessPreventionCodes.OK_CODE.getCode());
            resultMap.put("failMessage", null);

            // 토큰 생성
            String acessToken = TokenUtils.generateToken(userDto);
            Cookie acessCookie = new Cookie(AppConstants.ACCESS_TOKEN, acessToken);
            acessCookie.setHttpOnly(false);
            acessCookie.setPath("/");
            response.addCookie(acessCookie);

            JSONObject jsonObject = new JSONObject(resultMap);
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.println(jsonObject);
                printWriter.close();
            }
        } catch (Exception e) {
            throw new DevException(e);
        }

    }
}
