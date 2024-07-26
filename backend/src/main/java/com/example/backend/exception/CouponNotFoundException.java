package com.example.backend.exception;

public class CouponNotFoundException extends GlobalException{

    public CouponNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
