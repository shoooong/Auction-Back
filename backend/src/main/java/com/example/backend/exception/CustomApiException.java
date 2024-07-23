package com.example.backend.exception;
public class CustomApiException extends RuntimeException {
    public CustomApiException(String message) {
        super(message);
    }
}
