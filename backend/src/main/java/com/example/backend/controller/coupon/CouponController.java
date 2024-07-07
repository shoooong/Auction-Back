package com.example.backend.controller.coupon;


import com.example.backend.dto.coupon.CouponCreateDto;
import com.example.backend.dto.coupon.CouponIssueDto;
import com.example.backend.service.coupon.CouponIssueService;
import com.example.backend.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
@Log4j2
public class CouponController {

    private final CouponService couponService;
    private final CouponIssueService couponIssueService;

    @PostMapping("/create")
    public ResponseEntity<?> couponCreate(@RequestBody CouponCreateDto couponCreateDto){

        log.info(couponCreateDto);

        couponService.createCoupon(couponCreateDto);

        return ResponseEntity.ok(200);
    }

    @PostMapping("/{couponId}")
    public ResponseEntity<?> couponIssue(@PathVariable String couponId, @RequestParam String userId){
        log.info(couponId);
        log.info(userId);

        couponIssueService.issueCoupon(couponId, userId);

        return ResponseEntity.ok(200);
    }
}
