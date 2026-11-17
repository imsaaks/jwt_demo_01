package com.iung.dev.common.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.SuperBuilder;

@Getter
@Setter
@Schema(description = "로그인 응답 정보")
public class ResponseUserDto {
    @Schema(description = "Member 아이디")
    private String memberId;

    @Schema(description = "Member 이름")
    private String userName;
}
