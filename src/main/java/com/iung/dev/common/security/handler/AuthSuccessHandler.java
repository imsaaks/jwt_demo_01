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

            if (userDto.getUseYn().equals("N")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resultMap.put("userInfo", null);
                resultMap.put("resultCode", AccessPreventionCodes.USE_YN_N_CODE.getCode());
                resultMap.put("failMessage", "사용이 불가능한 계정입니다.");
            } else if (userDto.getDeleteYn().equals("Y")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resultMap.put("userInfo", null);
                resultMap.put("resultCode", AccessPreventionCodes.DELETE_YN_Y_CODE.getCode());
                resultMap.put("failMessage", "삭제된 계정입니다.");
            } else if (userDto.getWithdrawYn().equals("Y")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resultMap.put("userInfo", null);
                resultMap.put("resultCode", AccessPreventionCodes.WITHDRAW_YN_Y_CODE.getCode());
                resultMap.put("failMessage", "탈퇴된 계정입니다.");

            } else if (userDto.getLockYn().equals("Y")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resultMap.put("userInfo", null);
                resultMap.put("resultCode", AccessPreventionCodes.LOCK_YN_Y_CODE.getCode());
                resultMap.put("failMessage", "잠금된 계정입니다.");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                resultMap.put("userInfo", userDtoObject);
                resultMap.put("resultCode", AccessPreventionCodes.OK_CODE.getCode());
                resultMap.put("failMessage", null);

                // JWT 토큰 생성

                String acessToken = TokenUtils.generateToken(userDto);
                // resultMap.put("acess_token", acessToken);
                Cookie acessCookie = new Cookie(AppConstants.ACCESS_TOKEN, acessToken);
                acessCookie.setHttpOnly(false);
                acessCookie.setPath("/");
                response.addCookie(acessCookie);
            }

            if (!resultMap.get("resultCode").equals(AccessPreventionCodes.OK_CODE.getCode())) {
                // 토큰 삭제
                TokenUtils.deleteToken(response);
            }

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
