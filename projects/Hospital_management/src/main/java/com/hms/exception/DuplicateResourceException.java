package com.hms.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends HmsException {

    public DuplicateResourceException(String errorCode, String message) {
        super(errorCode, message, HttpStatus.CONFLICT);
    }
}
