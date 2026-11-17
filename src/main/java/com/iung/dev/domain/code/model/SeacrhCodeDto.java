package com.iung.dev.domain.code.model;

import com.iung.dev.common.model.SearchBaseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "코드검색")
public class SeacrhCodeDto extends SearchBaseDto {
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
    @Schema(description = "기본값여부", defaultValue = "N", allowableValues = {"Y", "N"})
    protected String defaultYn;

    @Parameter(description = "서브코드그룹")
    @Schema(description = "서브코드그룹")
    protected String subCodeGroup;

    @Parameter(description = "서브코드그룹명")
    @Schema(description = "서브코드그룹명")
    protected String subCodeGroupNm;

    @Parameter(description = "사용유무")
    @Schema(description = "사용유무", defaultValue = "Y", allowableValues = {"Y", "N"})
    protected String useYn;

    @Parameter(description = "삭제유무")
    @Schema(description = "삭제유무", defaultValue = "N", allowableValues = {"Y", "N"})
    protected String deleteYn;
}
