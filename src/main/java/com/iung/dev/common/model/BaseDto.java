package com.iung.dev.common.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto {
    @Parameter(description = "생성자ID")
    @Schema(description = "생성자ID", accessMode = Schema.AccessMode.READ_ONLY)
    protected String createdBy;

    @Parameter(description = "생성일시")
    @Schema(description = "생성일시", accessMode = Schema.AccessMode.READ_ONLY)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createdTime;

    @Parameter(description = "수정자ID")
    @Schema(description = "수정자ID", accessMode = Schema.AccessMode.READ_ONLY)
    protected String modifiedBy;

    @Parameter(description = "수정일시")
    @Schema(description = "수정일시", accessMode = Schema.AccessMode.READ_ONLY)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime modifiedTime;
}
