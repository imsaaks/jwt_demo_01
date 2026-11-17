package com.iung.dev.domain.code.model;

import com.iung.dev.common.model.BaseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "코드")
public class CodeDto extends BaseDto {

    @Parameter(description = "코드 그룹")
    @Schema(description = "코드 그룹")
    protected String codeGroup;

    @Parameter(description = "코드")
    @Schema(description = "코드")
    protected String code;

    @Parameter(description = "코드명")
    @Schema(description = "코드명")
    protected String codeNm;

    @Parameter(description = "코드설명")
    @Schema(description = "코드설명")
    protected String codeDesc;

    @Parameter(description = "정렬순서")
    @Schema(description = "정렬순서")
    protected Integer sortOrd;

    @Parameter(description = "기본값여부")
    @Schema(description = "기본값여부")
    protected String defaultYn;

    @Parameter(description = "서브코드그룹")
    @Schema(description = "서브코드그룹")
    protected String subCodeGroup;

    @Parameter(description = "서브코드그룹명")
    @Schema(description = "서브코드그룹명")
    protected String subCodeGroupNm;

    @Parameter(description = "사용유무")
    @Schema(description = "사용유무")
    protected String useYn;

    @Parameter(description = "삭제유무")
    @Schema(description = "삭제유무")
    protected String deleteYn;
}
