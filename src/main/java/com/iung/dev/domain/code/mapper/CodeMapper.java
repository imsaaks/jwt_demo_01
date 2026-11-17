package com.iung.dev.domain.code.mapper;


import com.iung.dev.domain.code.model.CodeDto;
import com.iung.dev.domain.code.model.SeacrhCodeDto;
import com.iung.dev.domain.user.model.SearchUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.*;


@Mapper
public interface CodeMapper {

    List<CodeDto> searchList(@Param("code") SeacrhCodeDto searchCodeDto, @Param("pageable") PageRequest pageRequest);
    int searchCount(@Param("code") SeacrhCodeDto searchCodeDto);

    Optional<CodeDto> findByGroupAndCode(@Param("groupCode") String groupCode, @Param("code") String code);

}
