package com.iung.dev.domain.user.model;

import com.iung.dev.common.model.BaseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "사용자등록")
public class RegistUserDto extends BaseDto {
    @Parameter(description = "Member 아이디")
    @Schema(description = "Member 아이디")
    protected String memberId;

    @Parameter(description = "Member 비밀번호")
    @Schema(description = "Member 비밀번호", accessMode = Schema.AccessMode.WRITE_ONLY)
    protected String memberPw;

    @Parameter(description = "Member 이름")
    @Schema(description = "Member 이름")
    protected String username;

    @Parameter(description = "연락처")
    @Schema(description = "연락처")
    protected String telno;

    @Parameter(description = "이메일")
    @Schema(description = "이메일")
    protected String email;

    @Parameter(description = "성별코드")
    @Schema(description = "성별코드")
    protected String sex;

    @Parameter(description = "비고")
    @Schema(description = "비고")
    protected String remark;
}
