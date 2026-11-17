package com.iung.dev.domain.user.controller;

import com.iung.dev.common.constants.AppConstants;
//import com.iung.dev.common.model.LoginDto;
import com.iung.dev.domain.user.model.RegistUserDto;
import com.iung.dev.domain.user.model.SearchUserDto;
import com.iung.dev.domain.user.model.ModifyUserDto;
import com.iung.dev.domain.user.model.UserDto;
import com.iung.dev.domain.user.model.UserIdCheckDto;
import com.iung.dev.domain.user.service.UserService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "회원등록/회원정보 API")
public class UserController {

    private final UserService userService;


    @Operation(summary = "아이디 중복 체크")
    @PostMapping("/checkId")
    public ResponseEntity<?> checkIdDuplicate(@RequestBody UserIdCheckDto userIdCheckDto) {
        return ResponseEntity.ok(userService.checkIdDuplicate(userIdCheckDto));
    }

    @Operation(summary = "회원등록")
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegistUserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }


    @Operation(summary = "회원(본인)정보 조회")
    @GetMapping("/me")
    public ResponseEntity<?> findMe(@CookieValue(AppConstants.ACCESS_TOKEN) Cookie coookie) {

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "회원정보 조회(단건)")
    @GetMapping("/find/{memberId}")
    public ResponseEntity<?> find(@PathVariable String memberId) {

        return ResponseEntity.ok(userService.findUser(memberId));
    }

    @Operation(summary = "회원정보 조회(다건)")
    @GetMapping("/search")
    public ResponseEntity<?> search(@ParameterObject SearchUserDto searchUserDto) {
        return ResponseEntity.ok(userService.search(searchUserDto));
    }



    @Operation(summary = "회원(본인)정보 수정")
    @PutMapping("/me")
    public ResponseEntity<?> updateMe(@RequestBody UserDto userDto, @CookieValue(AppConstants.ACCESS_TOKEN) Cookie coookie) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 정보 수정")
    @PutMapping("/update/{memberId}")
    public ResponseEntity<?> update(@PathVariable String memberId, @RequestBody ModifyUserDto userDto) {
        return ResponseEntity.ok(userService.update(memberId, userDto));
    }

    @Operation(summary = "회원(본인)비밀번호 수정")
    @PutMapping("/me/password")
    public ResponseEntity<?> updateMePassword(@RequestBody UserDto userDto, @CookieValue(AppConstants.ACCESS_TOKEN) Cookie coookie) {
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "회원 비밀번호 수정")
    @PutMapping("/update/password/{memberId}")
    public ResponseEntity<?> updatePassword(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().build();
    }

}
