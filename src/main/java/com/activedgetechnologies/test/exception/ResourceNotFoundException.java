package com.activedgetechnologies.test.exception;


public class ResourceNotFoundException extends Exception {


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super("Resource not Found");
    }

}

