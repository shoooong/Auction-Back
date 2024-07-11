package com.example.backend.dto.coupon;

import com.example.backend.entity.Coupon;
import com.example.backend.entity.enumData.DiscountType;
import java.time.LocalDateTime;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDto {
    private Long couponId;
    private String couponTitle;
    private Long couponQuantity;
    private Long maxQuantity;
    private String couponCode;
    private Long expDay;
    private DiscountType discountType;
    private Long amount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;

    public Coupon toEntity() {
        return Coupon.builder()
            .couponId(this.couponId)
            .couponTitle(this.couponTitle)
            .couponQuantity(this.couponQuantity)
            .maxQuantity(this.maxQuantity)
            .couponCode(this.couponCode)
            .expDay(this.expDay)
            .discountType(this.discountType)
            .amount(this.amount)
            .startDate(this.startDate)
            .endDate(this.endDate)
            .content(this.content)
            .build();
    }
}
