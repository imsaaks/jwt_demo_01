package com.iung.dev.common.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.iung.dev.common.exception.code.ErrorCodes;
import com.iung.dev.common.exception.code.GlobalErrorCodes;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.SuperBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;

@Getter
@Setter
@JsonPropertyOrder({ "code", "message", "details" })
public class ErrorResponse implements Serializable {
    String code;
    String message;
    Object details;


    public ErrorResponse(String message) {
        // empty
    }

    public ErrorResponse(ErrorCodes code, String message, Object details) {
        this.code = Preconditions.checkNotNull(code).getCode();
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(DevException e) {
        this.code = e.getCode() == null ? GlobalErrorCodes.DEFAULT_GLOBAL_ERROR_CODE.toString() : e.getCode().toString();
        this.message = e.getMessage();
        this.details = ExceptionUtils.getStackTrace(e);
    }

    public static ErrorResponse unknownError(Exception ex) {
        return new ErrorResponse(GlobalErrorCodes.DEFAULT_GLOBAL_ERROR_CODE, DevException.DEFAULT_GLOBAL_MESSAGE, ExceptionUtils.getStackTrace(ex));
    }


    @Override
    public String toString() {
        return "";
    }

}
