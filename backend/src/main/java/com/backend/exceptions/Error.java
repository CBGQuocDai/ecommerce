package com.backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum Error {
    UNAUTHENTICATED(1001, "unauthenticated", HttpStatus.UNAUTHORIZED),
    BAD_REQUEST(1002, "bad request", HttpStatus.BAD_REQUEST)
    ;
    private final int code;
    private final String message;
    private final HttpStatus status;
    Error(int code, String message, HttpStatus status) {
        this.code = code;
        this.message= message;
        this.status = status;
    }
}
