package com.iung.dev.common.security.exception;

import com.iung.dev.common.exception.code.ErrorCodes;

public enum AccessPreventionCodes implements ErrorCodes{
    OK_CODE("200"),
    ID_BLANK_CODE("9000"),
    ID_NOT_MATCH_CODE("9001"),
    ID_PWD_NOT_MATCH_CODE("9002"),
    USE_YN_N_CODE("9901"),
    DELETE_YN_Y_CODE("9902"),
    WITHDRAW_YN_Y_CODE("9903"),
    LOCK_YN_Y_CODE("9904"),
    UNKNOWN_CODE("9910"),
    DEFAULT_PREVENTION_CODE("9999");
    final String errorCode;

    AccessPreventionCodes(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getCode() {
        return errorCode;
    }
}
