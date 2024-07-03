package com.example.backend.service;

import com.example.backend.repository.UserCoupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;

    public Long getValidCouponCount(Long userId) {
        return userCouponRepository.countValidCouponsByUserId(userId);
    }
}
