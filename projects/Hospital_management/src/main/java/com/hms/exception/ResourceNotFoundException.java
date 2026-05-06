package com.hms.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends HmsException {

    public ResourceNotFoundException(String errorCode, String message) {
        super(errorCode, message, HttpStatus.NOT_FOUND);
    }
}
