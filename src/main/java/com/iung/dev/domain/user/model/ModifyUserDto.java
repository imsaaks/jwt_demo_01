package com.iung.dev.domain.user.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "사용자수정")
public class ModifyUserDto {
    @Parameter(description = "Member 아이디")
    @Schema(description = "Member 아이디", accessMode = Schema.AccessMode.READ_ONLY)
    protected String memberId;

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

    @Parameter(description = "사용여부")
    @Schema(description = "사용여부")
    protected String useYn;

    @Parameter(description = "삭제여부")
    @Schema(description = "삭제여부")
    protected String deleteYn;

    @Parameter(description = "탈퇴여부")
    @Schema(description = "탈퇴여부")
    protected String withdrawYn;

    @Parameter(description = "계정잠금여부")
    @Schema(description = "계정잠금여부")
    protected String lockYn;
}
