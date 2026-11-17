package com.iung.dev.domain.user.model;

import com.iung.dev.common.model.BaseDto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자")
public class UserDto extends BaseDto {
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

    @Parameter(description = "성별명")
    @Schema(description = "성별명")
    protected String sexNm;

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

    @Parameter(description = "로그인 실패 카운트")
    @Schema(description = "로그인 실패 카운트")
    protected Integer failedCount;

    @Parameter(description = "계정잠금여부")
    @Schema(description = "계정잠금여부")
    protected String lockYn;

}
