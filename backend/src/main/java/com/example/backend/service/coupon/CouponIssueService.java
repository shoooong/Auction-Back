package com.example.backend.service.coupon;

import com.example.backend.dto.coupon.CouponIssueDto;
import com.example.backend.entity.Coupon;
import com.example.backend.entity.CouponIssue;
import com.example.backend.entity.Users;
import com.example.backend.producer.CouponCreateProducer;
import com.example.backend.repository.CouponIssue.CouponIssueRepository;
import com.example.backend.repository.CouponIssue.RedisRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.coupon.CouponRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CouponIssueService {
    private final CouponIssueRepository couponIssueRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final RedisRepository redisRepository;

    public void couponIssue(String couponPolicyId, String userId){

        Users user = userRepository.findById(Long.valueOf(userId))
            .orElseThrow(() -> new RuntimeException("User not found"));
        Coupon coupon = couponRepository.findById(Long.valueOf(couponPolicyId))
            .orElseThrow(() -> new RuntimeException("Coupon not found"));

        CouponIssueDto couponIssueDto = new CouponIssueDto();
        CouponIssue couponIssue = couponIssueDto.toEntity(user, coupon);

        couponIssueRepository.save(couponIssue);
    }

    public void issueCoupon(String couponPolicyId, String userId) {

        // 해당 쿠폰 정책 발급수 INCR
        Long issuedCount = redisRepository.couponIssuedCount(couponPolicyId);

        if (issuedCount != null && issuedCount > 100) { //
            redisRepository.issuedCancel(couponPolicyId);
            System.out.println("Coupon limit reached");
            return;
        }

        boolean isUserAdded = redisRepository.add(couponPolicyId, userId);
        if (!isUserAdded) {
            System.out.println("User already received a coupon");
            redisRepository.issuedCancel(couponPolicyId);
            return;
        }
        couponCreateProducer.create(couponPolicyId, userId);
    }
//
//    public void issuedCoupon(CouponIssueDto couponIssueDto){
//        Long apply = issueListRepository.add(couponIssueDto.getUserId());
//
//        if (apply != 1) {
//            return;
//        }
//
//        Long count = couponCountRepository.increment();
//
//        if (count > 10) {
//            return;
//        }
//        couponCreateProducer.create(couponIssueDto);
//
//
//    }

}
