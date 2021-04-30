package com.activedgetechnologies.test.exception;

import javax.security.sasl.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private String message = "Expired or invalid JWT token";

    public InvalidJwtAuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidJwtAuthenticationException() {
        super("Expired or invalid JWT token");
    }

    @Override
    public String getMessage() {
        return message;
    }
}
