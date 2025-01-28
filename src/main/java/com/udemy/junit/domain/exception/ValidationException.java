package com.udemy.junit.domain.exception;

public class ValidationException extends RuntimeException{

    public static final long serivalVersionUID = 1L;

    public ValidationException(String message) {
        super(message);
    }

}
