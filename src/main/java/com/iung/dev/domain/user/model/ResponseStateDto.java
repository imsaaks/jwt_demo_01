package com.iung.dev.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.io.Serializable;

@Getter
@Setter
@Schema(description = "Token 상태 응답")
public class ResponseStateDto implements Serializable {
    private String state;
    private UserDto userInfo;
}
