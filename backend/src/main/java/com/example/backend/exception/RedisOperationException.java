package com.example.backend.exception;
public class RedisOperationException extends RuntimeException {
    public RedisOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
