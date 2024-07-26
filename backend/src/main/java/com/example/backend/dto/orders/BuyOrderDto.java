package com.example.backend.dto.orders;


import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyOrderDto {
    private Long productId;
//    private Long buyingBiddingId;
    private Long couponId;
    private Long addressId;
    private BigDecimal price;
    private Long exp;
}
