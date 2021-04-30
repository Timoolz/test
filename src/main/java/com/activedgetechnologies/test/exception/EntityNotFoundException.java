package com.activedgetechnologies.test.exception;


public class EntityNotFoundException extends Exception {


    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super("Entity not Found");
    }

}


