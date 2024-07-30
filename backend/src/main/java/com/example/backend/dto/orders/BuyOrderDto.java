package com.example.backend.dto.orders;


import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class BuyOrderDto {
    private Long productId;
    private Long couponId;
    private Long addressId;
    private BigDecimal price;
    private Long exp;
}
