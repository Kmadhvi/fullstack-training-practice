package com.hms.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HmsException {

    public UnauthorizedException(String message) {
        super("UNAUTHORIZED", message, HttpStatus.UNAUTHORIZED);
    }
}
