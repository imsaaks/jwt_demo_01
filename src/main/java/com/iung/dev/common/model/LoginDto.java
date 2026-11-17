package com.iung.dev.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인 요청 정보")
public class LoginDto {
    @Schema(description = "Member 아이디")
    private String memberId;
    @Schema(description = "Member 비밀번호")
    private String memberPw;
}
