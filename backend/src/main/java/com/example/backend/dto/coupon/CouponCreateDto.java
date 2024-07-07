package com.example.backend.dto.coupon;
import com.example.backend.entity.Coupon;
import com.example.backend.entity.enumData.DiscountType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponCreateDto {
    private String couponTitle;
    private Long couponQuantity;
    private Long maxQuantity;
    private String couponCode;
    private Long expDay;
    private DiscountType discountType;
    private Long amount;

    public Coupon toEntity() {
        return Coupon.builder()
            .couponTitle(this.couponTitle)
            .couponQuantity(this.couponQuantity)
            .maxQuantity(this.maxQuantity)
            .couponCode(this.couponCode)
            .expDay(this.expDay)
            .discountType(this.discountType)
            .amount(this.amount)
            .build();
    }
}
