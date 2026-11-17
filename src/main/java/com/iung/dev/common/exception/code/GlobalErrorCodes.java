package com.iung.dev.common.exception.code;

public enum GlobalErrorCodes implements ErrorCodes {
/**
 * ******************************* Global Error CodeList ***************************************
 * HTTP Status Code
 * 400 : Bad Request
 * 401 : Unauthorized
 * 403 : Forbidden
 * 404 : Not Found
 * 500 : Internal Server Error
 * *********************************************************************************************
 */

    DEFAULT_GLOBAL_ERROR_CODE("GBC000"),        // status 500
    UNKNOWN_SERVER_ERROR_CODE("GBC001"),        // status 500
    DATA_NOT_FOUND_CODE("GBC002"),              // status 404
    BAD_REQUEST_CODE("GBC003"),                 // status 400
    ACCESS_DENIED_CODE("GBC004"),               // status 403
    AUTH_ERROR_CODE("GBC005"),                  // status 401
    INVALID_USERNAME_PASSWORD_CODE("GBC006"),   // status 400
    INVALID_TOKEN_CODE("GBC007"),               // status 401
    DUPLICATE_DATA_CODE("GBC008"),              // status 400
    DEFAULT_GLOBAL_ERROR("default.global.error");
    final String errorCode;

    GlobalErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getCode() {
        return errorCode;
    }

}
