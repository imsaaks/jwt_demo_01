package com.iung.dev.domain.code.controller;

import com.iung.dev.domain.code.model.SeacrhCodeDto;
import com.iung.dev.domain.code.service.CodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/code")
@Tag(name = "Code", description = "코드 API")
public class CodeController {

    private final CodeService codeService;

    @Operation(summary = "코드정보 조회(다건)")
    @GetMapping("/search")
    public ResponseEntity<?> search(@ParameterObject SeacrhCodeDto searchCodeDto) {
        return ResponseEntity.ok(codeService.search(searchCodeDto));
    }

    @Operation(summary = "코드정보 조회(단건)")
    @GetMapping("/{groupCode}/{code}")
    public ResponseEntity<?> findByGroupAndCode(@PathVariable("groupCode") String groupCode, @PathVariable("code") String code){
        return ResponseEntity.ok(codeService.findByGroupAndCode(groupCode, code));
    }
}
