package com.example.backend.dto.orders;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaleOrderDto {
    private Long productId;
    private Long couponId;
    private Long salesBiddingId;

}
