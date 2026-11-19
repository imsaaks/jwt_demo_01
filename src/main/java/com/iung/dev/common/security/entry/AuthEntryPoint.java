package com.iung.dev.common.security.entry;

import com.iung.dev.common.security.exception.AccessPreventionCodes;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    // 401
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()){
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("userInfo", null);
            resultMap.put("resultCode", AccessPreventionCodes.DEFAULT_PREVENTION_CODE.getCode());
            resultMap.put("message", "인증실패 : " + authException.getMessage());
            writer.print(new JSONObject(resultMap));
            writer.flush();
            writer.close();
        }
    }
}
