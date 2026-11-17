package com.iung.dev.domain.user.model;


import com.iung.dev.common.model.SearchBaseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자검색")
public class SearchUserDto extends SearchBaseDto {

    @Parameter(description = "Member 아이디")
    @Schema(description = "Member 아이디")
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
    @Schema(description = "성별코드", defaultValue = "0", allowableValues = {"0", "1"})
    protected String sex;

    @Parameter(description = "비고")
    @Schema(description = "비고")
    protected String remark;

    @Parameter(description = "사용여부")
    @Schema(description = "사용여부", defaultValue = "Y", allowableValues = {"Y", "N"})
    protected String useYn;

    @Parameter(description = "삭제여부")
    @Schema(description = "삭제여부", defaultValue = "N", allowableValues = {"Y", "N"})
    protected String deleteYn;

    @Parameter(description = "탈퇴여부")
    @Schema(description = "탈퇴여부", defaultValue = "N", allowableValues = {"Y", "N"})
    protected String withdrawYn;

    @Parameter(description = "계정잠금여부")
    @Schema(description = "계정잠금여부", defaultValue = "N", allowableValues = {"Y", "N"})
    protected String lockYn;

//    @Parameter(description = "로그인 실패 카운트")
//    @Schema(description = "로그인 실패 카운트", accessMode = Schema.AccessMode.READ_ONLY)
//    protected Integer failedCount;
}
