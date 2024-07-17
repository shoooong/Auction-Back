package com.example.backend.dto.orders;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyOrderDto {
    private Long productId;
    private Long buyingBiddingId;
    private Long couponId;
}
