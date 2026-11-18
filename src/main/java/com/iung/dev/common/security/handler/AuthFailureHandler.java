package com.iung.dev.common.security.handler;

import com.iung.dev.common.utils.TokenUtils;
import com.iung.dev.domain.user.service.UserService;
import com.iung.dev.common.security.exception.AccessPreventionCodes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {


    @Autowired
    private UserService userService;

    // Unauthorized
    @Override
    public 	void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException exception) throws IOException {

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errorCode = exception.getMessage();
        String failMessage = "아이디 또는 비밀번호가 올바르지 않습니다. 입력한 정보를 다시 확인해 주세요.";

        if (errorCode.equals(AccessPreventionCodes.USE_YN_N_CODE.getCode())) {
            failMessage = "사용이 불가능한 계정입니다.";
        } else if (errorCode.equals(AccessPreventionCodes.DELETE_YN_Y_CODE.getCode())) {
            failMessage = "삭제된 계정입니다.";
        } else if (errorCode.equals(AccessPreventionCodes.WITHDRAW_YN_Y_CODE.getCode())) {
            failMessage = "탈퇴된 계정입니다.";
        } else if (errorCode.equals(AccessPreventionCodes.LOCK_YN_Y_CODE.getCode())) {
            failMessage = "잠금된 계정입니다.";
        }
        // 토큰 삭제
        TokenUtils.deleteToken(response);

        try (PrintWriter printWriter = response.getWriter()) {
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("userInfo", null);
            resultMap.put("resultCode", AccessPreventionCodes.ID_PWD_NOT_MATCH_CODE.getCode());
            resultMap.put("failMessage", failMessage);
            printWriter.println(new JSONObject(resultMap));
            printWriter.close();
        }
    }

}
