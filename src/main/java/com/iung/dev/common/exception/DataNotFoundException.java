package com.iung.dev.common.exception;

import com.iung.dev.common.exception.code.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.iung.dev.common.exception.code.GlobalErrorCodes.DATA_NOT_FOUND_CODE;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Data not found")
public class DataNotFoundException extends DevException {

    public DataNotFoundException(ErrorCodes errorCodes, String message, Throwable cause) {
        super(errorCodes, message, cause);
    }

    public DataNotFoundException(ErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }

    public DataNotFoundException(ErrorCodes errorCodes, Throwable cause) {
        super(errorCodes, cause);
    }

    public DataNotFoundException(String message) {
        super(DATA_NOT_FOUND_CODE, message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(DATA_NOT_FOUND_CODE, message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(DATA_NOT_FOUND_CODE, cause);
    }
}
