package com.example.backend.exception;

public class CustomUserException extends RuntimeException {
    public CustomUserException(String message) {
        super(message);
    }
}
