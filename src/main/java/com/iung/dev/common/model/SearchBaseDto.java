package com.iung.dev.common.model;


import com.iung.dev.common.utils.CommonUtils;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchBaseDto implements Serializable {

    @Parameter(description = "페이지번호", example = "0")
    @Schema(description = "페이지번호", defaultValue = "0")
    protected Integer pageNo;

    @Parameter(description = "페이지사이즈", example = "10")
    @Schema(description = "페이지사이즈", defaultValue = "10")
    protected Integer pageSize;

    @Parameter(description = "정렬컬럼")
    @Schema(description = "정렬컬럼")
    protected String sortBy;

    @Parameter(description = "정렬방법(example: desc, asc)", example = "desc")
    @Schema(description = "정렬방법(example: desc, asc)", example = "desc", defaultValue = "desc")
    protected String sortOrder;

    public PageRequest getPageRequest() {
        int defaultPageSize = 10;
        int defaultPageNo = 0;
        int pageSize = (this.pageSize != null && this.pageSize > 0) ? this.pageSize : defaultPageSize;
        int pageNo = (this.pageNo != null) ? this.pageNo : defaultPageNo;
        Sort.Direction userDirection = "asc".equalsIgnoreCase(this.sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (!StringUtils.isEmpty(this.sortBy)) {
            Sort sort = Sort.by(new Sort.Order(userDirection, CommonUtils.camelToUpperSnakeCase(this.sortBy)));
            return PageRequest.of(pageNo, pageSize, sort);
        }
        return PageRequest.of(pageNo, pageSize);

    }

    public PageRequest getPageRequestExceptConversion() {
        int defaultPageSize = 10;
        int defaultPageNo = 0;
        int pageSize = (this.pageSize != null && this.pageSize > 0) ? this.pageSize : defaultPageSize;
        int pageNo = (this.pageNo != null) ? this.pageNo : defaultPageNo;
        Sort.Direction userDirection = "asc".equalsIgnoreCase(this.sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (!StringUtils.isEmpty(this.sortBy)) {
            Sort sort = Sort.by(new Sort.Order(userDirection, this.sortBy));
            return PageRequest.of(pageNo, pageSize, sort);
        }
        return PageRequest.of(pageNo, pageSize);
    }
}
