package com.iung.dev.common.security.service;

import com.iung.dev.common.security.model.SecurityUserDto;
import com.iung.dev.common.security.exception.AccessPreventionCodes;
import com.iung.dev.domain.user.mapper.UserMapper;
import com.iung.dev.domain.user.model.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserById(username);
    };

    @Transactional
    public UserDetails loadUserById(String memberId) throws UsernameNotFoundException {
        UserDto userDto = userMapper.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(AccessPreventionCodes.ID_NOT_MATCH_CODE.getCode()));
        return this.getSecurityUserDto(userDto);
    }

    @Transactional
    public String loadUserPwd(String memberId){
        return userMapper.findPwd(memberId);
    }


    @Transactional
    public int incrementFailedCount(String memberId) {
        return userMapper.incrementFailedCount(memberId);
    }


    @Transactional
    public int resetFailedCount(String memberId) {
        return userMapper.resetFailedCount(memberId);
    }


    private static SecurityUserDto getSecurityUserDto(UserDto user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new SecurityUserDto(user, authorities);
    }
}
