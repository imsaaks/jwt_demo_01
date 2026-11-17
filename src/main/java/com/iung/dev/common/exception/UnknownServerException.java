package com.iung.dev.common.exception;

import com.iung.dev.common.exception.code.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.iung.dev.common.exception.code.GlobalErrorCodes.UNKNOWN_SERVER_ERROR_CODE;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unknown server error")
public class UnknownServerException extends DevException {

    public UnknownServerException(ErrorCodes errorCodes, String message, Throwable cause) {
        super(errorCodes, message, cause);
    }

    public UnknownServerException(ErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }

    public UnknownServerException(ErrorCodes errorCodes, Throwable cause) {
        super(errorCodes, cause);
    }

    public UnknownServerException(String message) {
        super(UNKNOWN_SERVER_ERROR_CODE, message);
    }

    public UnknownServerException(String message, Throwable cause) {
        super(UNKNOWN_SERVER_ERROR_CODE, message, cause);
    }

    public UnknownServerException(Throwable cause) {
        super(UNKNOWN_SERVER_ERROR_CODE, cause);
    }
}
