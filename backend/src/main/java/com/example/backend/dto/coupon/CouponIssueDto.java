package com.example.backend.dto.coupon;

import com.example.backend.entity.Coupon;
import com.example.backend.entity.CouponIssue;
import com.example.backend.entity.Users;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponIssueDto {
    private Long userId;
    private Long couponId;

    public CouponIssue toEntity(Users user, Coupon coupon){
        return CouponIssue.builder()
            .user(user)
            .coupon(coupon)
            .endDate(LocalDate.now().plusDays(coupon.getExpDay()))
            .build();
    }
}
