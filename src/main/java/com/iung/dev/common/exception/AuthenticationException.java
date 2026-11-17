package com.iung.dev.common.exception;

import com.iung.dev.common.exception.code.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.iung.dev.common.exception.code.GlobalErrorCodes.AUTH_ERROR_CODE;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Authentication failed")
public class AuthenticationException extends DevException {

    public AuthenticationException(ErrorCodes errorCodes, String message, Throwable cause) {
        super(errorCodes, message, cause);
    }

    public AuthenticationException(ErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }

    public AuthenticationException(ErrorCodes errorCodes, Throwable cause) {
        super(errorCodes, cause);
    }

    public AuthenticationException(String message) {
        super(AUTH_ERROR_CODE, message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(AUTH_ERROR_CODE, message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(AUTH_ERROR_CODE, cause);
    }
}
