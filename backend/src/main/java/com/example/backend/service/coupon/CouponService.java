package com.example.backend.service.coupon;

import com.example.backend.dto.coupon.CouponCreateDto;
import com.example.backend.entity.Coupon;
import com.example.backend.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CouponService {

    private final CouponRepository couponRepository;

    public void createCoupon(CouponCreateDto couponCreateDto) {
        Coupon coupon = couponCreateDto.toEntity();

        couponRepository.save(coupon);
    }
}
