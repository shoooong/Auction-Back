package com.example.backend.service.coupon;

import com.example.backend.dto.coupon.CouponCreateDto;
import com.example.backend.dto.coupon.CouponDto;
import com.example.backend.entity.Coupon;
import com.example.backend.entity.enumData.CouponCondition;
import com.example.backend.repository.CouponIssue.RedisRepository;
import com.example.backend.repository.coupon.CouponRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CouponService {

    private final CouponRepository couponRepository;
    private final RedisRepository redisRepository;

    public void createCoupon(CouponCreateDto couponCreateDto) {
        Coupon coupon = couponCreateDto.toEntity();


        /* MySQL에 Coupon 저장
         * */

        Coupon savedCoupon = couponRepository.save(coupon);
        String couponSaveId = String.valueOf(savedCoupon.getCouponId());

        /* Redis에 Coupon 발급조건 저장
         *  발급 수량, 발급 시작 날짜&시간 , 발급 종료 날짜&시간, 쿠폰 발급코드
         * */


        redisRepository.saveCouponCondition(couponSaveId, CouponCondition.MAX_QUANTITY,
            String.valueOf(coupon.getMaxQuantity()));
        redisRepository.saveCouponCondition(couponSaveId, CouponCondition.START_DATE,
            String.valueOf(coupon.getStartDate()));
        redisRepository.saveCouponCondition(couponSaveId, CouponCondition.END_DATE,
            String.valueOf(coupon.getEndDate()));
        redisRepository.saveCouponCondition(couponSaveId, CouponCondition.COUPON_CODE, String.valueOf(coupon.getCouponCode()));
    }

    // 이벤트 쿠폰 조회
    public List<CouponDto> searchCouponsByTitle(String keyword) {
        List<Coupon> coupons = couponRepository.findByCouponTitleContaining(keyword);
        return coupons.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private CouponDto convertToDto(Coupon coupon) {
        return CouponDto.builder()
            .couponId((coupon.getCouponId()))
            .couponTitle(coupon.getCouponTitle())
            .couponQuantity(coupon.getCouponQuantity())
            .maxQuantity(coupon.getMaxQuantity())
            .couponCode(coupon.getCouponCode())
            .expDay(coupon.getExpDay())
            .discountType(coupon.getDiscountType())
            .amount(coupon.getAmount())
            .startDate(coupon.getStartDate())
            .endDate(coupon.getEndDate())
            .content(coupon.getContent())
            .build();
    }
}
