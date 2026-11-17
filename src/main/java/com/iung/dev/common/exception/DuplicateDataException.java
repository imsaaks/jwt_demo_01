package com.iung.dev.common.exception;

import com.iung.dev.common.exception.code.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.iung.dev.common.exception.code.GlobalErrorCodes.DUPLICATE_DATA_CODE;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class DuplicateDataException extends DevException {

    public DuplicateDataException(ErrorCodes errorCodes, String message, Throwable cause) {
        super(errorCodes, message, cause);
    }

    public DuplicateDataException(ErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }

    public DuplicateDataException(ErrorCodes errorCodes, Throwable cause) {
        super(errorCodes, cause);
    }

    public DuplicateDataException(String message) {
        super(DUPLICATE_DATA_CODE, message);
    }

    public DuplicateDataException(String message, Throwable cause) {
        super(DUPLICATE_DATA_CODE, message, cause);
    }

    public DuplicateDataException(Throwable cause) {
        super(DUPLICATE_DATA_CODE, cause);
    }
}
