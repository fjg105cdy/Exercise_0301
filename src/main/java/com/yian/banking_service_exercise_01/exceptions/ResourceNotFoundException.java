package com.yian.banking_service_exercise_01.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message,String fieldName, Object fieldValue ) {
        super(String.format("%s with %s: %s not found",message,fieldName,fieldValue));
    }

}
