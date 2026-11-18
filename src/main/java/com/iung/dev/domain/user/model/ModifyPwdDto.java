package com.iung.dev.domain.user.model;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "비밀번호 변경 DTO")
public class ModifyPwdDto {

    @Parameter(description = "변경 전 비밀번호")
    @Schema(description = "변경 전 비밀번호")
    protected String memberPw;

    @Parameter(description = "변경 후 비밀번호")
    @Schema(description = "변경 후 비밀번호")
    protected String memberNewPw;
}
