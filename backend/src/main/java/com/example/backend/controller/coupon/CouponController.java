package com.example.backend.controller.coupon;


import com.example.backend.dto.coupon.CouponCreateDto;
import com.example.backend.dto.coupon.CouponDto;
import com.example.backend.dto.coupon.CouponIssueDto;
import com.example.backend.dto.coupon.UserCouponDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.service.alarm.AlarmService;
import com.example.backend.service.coupon.CouponIssueService;
import com.example.backend.service.coupon.CouponService;
import java.util.List;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CouponController {

    private final CouponService couponService;
    private final CouponIssueService couponIssueService;

    private final AlarmService alarmService;

    @GetMapping("/coupon/time-attack")
    public ResponseEntity<List<CouponDto>> timeAttack(){
        List<CouponDto> couponDto = couponService.searchCouponsByTitle("timeAttack");

        return new ResponseEntity<>(couponDto, HttpStatus.OK);
    }
    @PostMapping("/api/coupon/create")
    public ResponseEntity<?> couponCreate(@RequestBody CouponCreateDto couponCreateDto){
        couponService.createCoupon(couponCreateDto);

        return ResponseEntity.status(HttpStatus.OK).body(couponCreateDto);
    }

    @PostMapping("/api/coupon/{couponId}/issue")
    public ResponseEntity<?> couponIssue(@PathVariable Long couponId, @AuthenticationPrincipal UserDTO userDTO){
        couponIssueService.issueCoupon(couponId, userDTO.getUserId());

        // 알림 전송
        alarmService.sendNotification(userDTO.getUserId(), AlarmType.COUPON);

        return ResponseEntity.ok(200);
    }



    @GetMapping("/api/coupon/user")
    public ResponseEntity<List<UserCouponDto>> userCoupons(@AuthenticationPrincipal UserDTO userDTO){
        List<UserCouponDto> userCoupons= couponIssueService.userCoupons(userDTO.getUserId());

        return new ResponseEntity<>(userCoupons, HttpStatus.OK);
    }


}
