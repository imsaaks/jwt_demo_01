package com.iung.dev.domain.user.controller;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.model.LoginDto;
import com.iung.dev.common.enumerate.TokenState;
import com.iung.dev.common.utils.TokenUtils;
import com.iung.dev.domain.user.model.RegistUserDto;
import com.iung.dev.domain.user.model.ResponseStateDto;

import com.iung.dev.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
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

    private final UserService userService;

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {
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

    /**
     *
     * @param cookie
     * @return
     * Frontend 에서 guard 또는 loader 사용할 때 호출
     */
    @Operation(summary = "토큰 유효성 검사")
    @GetMapping("/check/token")
    public ResponseEntity<?> checkToken(@Parameter(hidden = true) @CookieValue(value = AppConstants.ACCESS_TOKEN) Cookie cookie) {
        ResponseStateDto responseStateDto = new ResponseStateDto();
        if (cookie != null) {
            String token = cookie.getValue();
            TokenState state = TokenUtils.getTokenState(token);
            responseStateDto.setState(state.getCode());
            responseStateDto.setUserInfo(null);
            if (state == TokenState.NORMAL) {
                String memberId = TokenUtils.getUserLoginIdFromToken(token);
                if (memberId != null) {
                    responseStateDto.setUserInfo(userService.findUser(memberId));
                }
            }
        } else {
            responseStateDto.setUserInfo(null);
            responseStateDto.setState(TokenState.TOKEN_EMPTY.getCode());
        }
        return ResponseEntity.ok(responseStateDto);
    }
}
