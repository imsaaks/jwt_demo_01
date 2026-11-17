package com.iung.dev.common.security.handler;

import com.iung.dev.domain.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.HashMap;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {


    @Autowired
    private UserService userService;

    // Unauthorized
    @Override
    public 	void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException exception) throws IOException {

        String memberId = request.getParameter("memberId");
        userService.incrementFailedCount(memberId);
        /* 로그인 실패 횟수 카운트 증가 및 계정 잠금(Lock) 처리 */
        //
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try(PrintWriter printWriter = response.getWriter()) {
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("userInfo", null);
            resultMap.put("resultCode", 9999);
            resultMap.put("failMessage", "인증 실패");
            printWriter.println(new JSONObject(resultMap));
            printWriter.close();
        }
    }

}
