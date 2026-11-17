package com.iung.dev.common.exception;

import com.google.common.base.Preconditions;
import com.iung.dev.common.exception.code.ErrorCodes;
import com.iung.dev.common.exception.code.GlobalErrorCodes;

public class DevException extends RuntimeException {

    public static final String DEFAULT_GLOBAL_MESSAGE = "Dev Exception";
    ErrorCodes code;

    public DevException(ErrorCodes code,  String message, Throwable cause) {
        super(message, cause);
        this.code = Preconditions.checkNotNull(code);
    }

    public DevException(ErrorCodes code, String message) {
        this(code, message, null);
    }

    public DevException (ErrorCodes code, Throwable cause) {
        this(code, cause.getMessage(), cause);
    }

    public DevException (String message) {
        super(message);
    }
    public DevException (String message, Throwable cause) {
        super(message, cause);
    }
    public DevException (Throwable cause) {
        super(cause);
    }

    public ErrorCodes getCode() {
        if(code == null) {
            return GlobalErrorCodes.DEFAULT_GLOBAL_ERROR;
        }

        return code;
    }
}
