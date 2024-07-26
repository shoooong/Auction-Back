package com.example.backend.exception;


// 쿠폰 중복 발급 예외
public class DuplicateCouponException extends GlobalException{

    public DuplicateCouponException(ErrorCode errorCode) {
        super(errorCode);
    }
}
