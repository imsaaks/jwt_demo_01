package com.iung.dev.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 응답 정보")
public class ResponseUserDto {
    @Schema(description = "Member 아이디")
    private String memberId;

    @Schema(description = "Member 이름")
    private String userName;
}
