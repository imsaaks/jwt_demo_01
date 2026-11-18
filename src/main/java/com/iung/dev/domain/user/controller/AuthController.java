package com.iung.dev.domain.user.controller;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.model.LoginDto;
import com.iung.dev.common.enumerate.TokenState;
import com.iung.dev.domain.user.model.RegistUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "sign In / sign Out", description = "로그인/로그아웃/토큰체크 API")
public class AuthController {

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "로그아웃")
    @GetMapping("/signout")
    public ResponseEntity<?> signOut() {

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토큰 유효성 검사")
    @GetMapping("/check/token")
    public ResponseEntity<?> checkToken(@CookieValue(AppConstants.ACCESS_TOKEN) String accessToken) {
        RegistUserDto registUserDto = new RegistUserDto();

        return ResponseEntity.ok().build();
    }

}
