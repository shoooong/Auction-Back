package com.example.backend.service.coupon;

import com.example.backend.dto.coupon.CouponDto;
import com.example.backend.dto.coupon.CouponIssueDto;
import com.example.backend.dto.coupon.UserCouponDto;
import com.example.backend.entity.Coupon;
import com.example.backend.entity.CouponIssue;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.CouponCondition;
import com.example.backend.producer.CouponCreateProducer;
import com.example.backend.repository.CouponIssue.CouponIssueRepository;
import com.example.backend.repository.CouponIssue.RedisRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.coupon.CouponRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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


    @Transactional
    public void couponIssue(String couponId, String userId){


        /* MySQL에 Coupon 저장
        * */
        Users user = userRepository.findById(Long.valueOf(userId))
            .orElseThrow(() -> new RuntimeException("User not found"));
        Coupon coupon = couponRepository.findById(Long.valueOf(couponId))
            .orElseThrow(() -> new RuntimeException("Coupon not found"));

        CouponIssueDto couponIssueDto = new CouponIssueDto();
        CouponIssue couponIssue = couponIssueDto.toEntity(user, coupon);

        couponIssueRepository.save(couponIssue);


    }


    public void issueCoupon(Long couponId, Long userId) {

        // 해당 쿠폰 정책 발급수 INCR
        Long issuedCount = redisRepository.couponIssuedCount(couponId);
        Long MAX_QUANTITY = Long.valueOf(
            redisRepository.getCouponCondition(couponId, CouponCondition.MAX_QUANTITY.name()));

        if (issuedCount != null && issuedCount > MAX_QUANTITY) { //
            redisRepository.issuedCancel(couponId);
            System.out.println("쿠폰 발급 수 초과");
            return;
        }


        String startDate = redisRepository.getCouponCondition(couponId, CouponCondition.START_DATE.name());
        String endDate = redisRepository.getCouponCondition(couponId, CouponCondition.END_DATE.name());
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);

        LocalDateTime currentDateTime = LocalDateTime.now();

        System.out.println("startDateTime = " + startDateTime);
        System.out.println("endDateTime = " + endDateTime);
        System.out.println("currentDateTime = " + currentDateTime);

        if (currentDateTime.isBefore(startDateTime) || currentDateTime.isAfter(endDateTime)) {
            System.out.println("쿠폰 발급 시간이 아닙니다.");
            return;
        }

        Long apply = redisRepository.registerCouponUser(couponId, userId);
        if (apply != 1) {
            System.out.println("이미 발급 받음");
            redisRepository.issuedCancel(couponId);
            return;
        }
        couponCreateProducer.create(couponId, userId);
    }
//    public List<CouponIssueDto> userCoupons(Long userId) {
//        List<CouponIssue> coupon = couponIssueRepository.findUnusedCouponsByUserId(userId);
//        for(CouponIssue couponIssue : coupon) {
//            convertToDto(couponIssue)
//        }
//
//
//    }

    public List<UserCouponDto> userCoupons(Long userId) {
        List<CouponIssue> coupons = couponIssueRepository.findUnusedCouponsByUserId(userId);
        return coupons.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public UserCouponDto convertToDto(CouponIssue couponIssue) {
        return UserCouponDto.builder()
            .coupon(couponIssue.getCoupon())
            .userId(couponIssue.getUser().getUserId())
            .build();
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
