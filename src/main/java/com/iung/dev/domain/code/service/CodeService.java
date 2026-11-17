package com.iung.dev.domain.code.service;

import com.iung.dev.common.exception.DataNotFoundException;
import com.iung.dev.domain.code.model.CodeDto;
import com.iung.dev.domain.code.model.SeacrhCodeDto;
import com.iung.dev.domain.code.mapper.CodeMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeMapper codeMapper;

    public Page<CodeDto> search(SeacrhCodeDto searchCodeDto) {
        List<CodeDto> codeDtoList = codeMapper.searchList(searchCodeDto, searchCodeDto.getPageRequest());
        int total = codeMapper.searchCount(searchCodeDto);
        return new PageImpl<>(codeDtoList, searchCodeDto.getPageRequest(), total);
    }

    public CodeDto findByGroupAndCode(String groupCode, String code) {
        return codeMapper.findByGroupAndCode(groupCode, code).orElseThrow(() -> new DataNotFoundException("Code not found"));
    }
}
