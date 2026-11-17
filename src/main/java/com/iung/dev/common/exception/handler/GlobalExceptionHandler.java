package com.iung.dev.common.exception.handler;

import com.iung.dev.common.exception.DevException;
import com.iung.dev.common.exception.ErrorResponse;
import com.iung.dev.common.exception.code.GlobalErrorCodes;
import com.iung.dev.common.exception.BadRequestException;
import com.iung.dev.common.exception.UnknownServerException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLSyntaxErrorException;

@Slf4j
@RestControllerAdvice( basePackages = {"com.iung.dev", "org.springframework.security"})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
        //
    }

    public ResponseEntity<Object> exceptionHandler(IOException ex, WebRequest request) {
        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(ex), "Broken pipe")) {
            return null;
        }
        return handleAll(ex, request);
    }

    @ExceptionHandler({SQLSyntaxErrorException.class, BadSqlGrammarException.class})
    protected ResponseEntity<Object> handleSQLSyntaxError(SQLSyntaxErrorException ex, WebRequest request) {
        String exceptionMessage = "SQL 처리 중 오류가 발생했습니다";

        log.error("SQL Syntax Error: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(GlobalErrorCodes.UNKNOWN_SERVER_ERROR_CODE, exceptionMessage, ex.getMessage());

        log.error(ExceptionUtils.getStackTrace(ex));
        log.error("[API:{}] {} {}: {}", ((ServletWebRequest) request).getRequest().getRequestURI(),
                response.getCode() == null ? "" : response.getCode(),
                response.getMessage(),
                response.getDetails());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }



    @ExceptionHandler(value = {DevException.class})
    protected ResponseEntity<Object> handleDevException(DevException ex, WebRequest request) {

        HttpStatus status;
        ErrorResponse response;

        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);

        String details = ExceptionUtils.getRootCauseMessage(ex);

        if (responseStatus != null) {
            status = responseStatus.code();
            response = new ErrorResponse(ex.getCode(), ex.getMessage(), details);
            log.info("ERROR :: {}", ex);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response = new ErrorResponse(ex.getCode(), DevException.DEFAULT_GLOBAL_MESSAGE, details);
        }

        log.error(ExceptionUtils.getStackTrace(ex));
        log.error("[API:{}] {} {}: {}", ((ServletWebRequest) request).getRequest().getRequestURI(),
                response.getCode() == null ? "" : response.getCode(),
                response.getMessage(),
                response.getDetails());
        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return handleDevException(new BadRequestException(ex), request);
    }


    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        return handleDevException(new UnknownServerException(ex), request);
    }
}
