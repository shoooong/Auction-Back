package com.example.backend.service;

import com.example.backend.repository.CouponIssue.CouponIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final CouponIssueRepository userCouponRepository;

    public Long getValidCouponCount(Long userId) {
        return userCouponRepository.countValidCouponsByUserId(userId);
    }
}
