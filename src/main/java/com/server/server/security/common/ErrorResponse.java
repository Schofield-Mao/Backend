package com.server.server.security.common;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Data
public class ErrorResponse {
    private final HttpStatus status;
    private final String msg;
    private final ErrorCode errorCode;
    private final Date timestamp;
    protected ErrorResponse(final String msg, final ErrorCode errorcode, HttpStatus status){
        this.msg = msg;
        this.errorCode = errorcode;
        this.status = status;
        this.timestamp = new Date();
    }

    public static ErrorResponse of(final String msg, final ErrorCode errorcode, HttpStatus status) {
        return new ErrorResponse(msg,errorcode,status);
    }
}
