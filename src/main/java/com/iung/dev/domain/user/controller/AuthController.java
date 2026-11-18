package com.iung.dev.domain.user.controller;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.model.LoginDto;
import com.iung.dev.common.enumerate.TokenState;
import com.iung.dev.common.utils.TokenUtils;
import com.iung.dev.domain.user.model.RegistUserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "sign In / sign Out", description = "로그인/로그아웃/토큰체크 API")
public class AuthController {

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {
        log.info("로그인 시도 {}", loginDto.getMemberId());
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "로그아웃")
    @GetMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response) {
        TokenUtils.deleteToken(response);
        HashMap<String, String> result = new HashMap();
        result.put("result", "OK");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "토큰 유효성 검사")
    @GetMapping("/check/token")
    public ResponseEntity<?> checkToken(@CookieValue(AppConstants.ACCESS_TOKEN) String accessToken) {
        RegistUserDto registUserDto = new RegistUserDto();

        return ResponseEntity.ok().build();
    }

}
