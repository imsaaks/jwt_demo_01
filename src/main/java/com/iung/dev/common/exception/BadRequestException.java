package com.iung.dev.common.exception;

import com.iung.dev.common.exception.code.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.iung.dev.common.exception.code.GlobalErrorCodes.BAD_REQUEST_CODE;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class BadRequestException extends DevException {

    public BadRequestException(ErrorCodes errorCodes, String message, Throwable cause) {
        super(errorCodes, message, cause);
    }

    public BadRequestException(ErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }

    public BadRequestException(ErrorCodes errorCodes, Throwable cause) {
        super(errorCodes, cause);
    }

    public BadRequestException(String message) {
        super(BAD_REQUEST_CODE, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(BAD_REQUEST_CODE, message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(BAD_REQUEST_CODE, cause);
    }

}
