package com.iung.dev.common.enumerate;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Schema(description = "토큰 상태")
public enum TokenState implements BaseEnum {
    NORMAL("NORMAL", "NORMAL"),
    EXPIRED("EXPIRED", "EXPIRED"),
    TAMPERED("TAMPERED", "TAMPERED"),
    TOKEN_EMPTY("TOKEN_EMPTY", "TOKEN_EMPTY"),
    USER_NOT_FOUND("USER_NOT_FOUND", "USER_NOT_FOUND");

    private final String code;
    private final String name;
}
