package com.iung.dev.domain.user.mapper;

import com.iung.dev.domain.user.model.RegistUserDto;
import com.iung.dev.domain.user.model.SearchUserDto;
import com.iung.dev.domain.user.model.ModifyUserDto;
import com.iung.dev.domain.user.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@Mapper
public interface UserMapper {

    List<UserDto> searchList(@Param("user") SearchUserDto searchUserDto, @Param("pageable") PageRequest pageRequest);
    int searchCount(@Param("user") SearchUserDto searchUserDto);

    Optional<UserDto> findById(@Param("memberId") String memberId);

    int checkMemberIdExists(@Param("memberId") String memberId);

    int register(@Param("user")  RegistUserDto user);

    int update(@Param("memberId") String memberId, @Param("user") ModifyUserDto user);

    int updatePwd(@Param("user") UserDto user);

    int incrementFailedCount(@Param("memberId") String memberId);



    /**
     * AuthenticationProvider 에서 사용
     */
    String findPwd(@Param("memberId") String memberId);
}
