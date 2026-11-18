package com.iung.dev.common.security.handler;

import com.iung.dev.domain.user.service.UserService;
import jakarta.servlet.ServletException;
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
        //
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter printWriter = response.getWriter()) {
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("userInfo", null);
            resultMap.put("resultCode", 9999);
            resultMap.put("failMessage", "아이디 또는 비밀번호가 올바르지 않습니다. 입력한 정보를 다시 확인해 주세요.");
            printWriter.println(new JSONObject(resultMap));
            printWriter.close();
        }
    }

}
