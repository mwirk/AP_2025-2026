package com.techcorp.employee.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
