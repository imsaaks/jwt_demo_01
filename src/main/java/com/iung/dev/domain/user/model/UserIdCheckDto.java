package com.iung.dev.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자 ID 중복확인")
public class UserIdCheckDto {
    @Schema(description = "중복확인용 Member 아이디")
    private String memberId;
}