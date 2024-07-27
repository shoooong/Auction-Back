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
public class SaleOrderDto {
    private Long productId;
//    private Long salesBiddingId;
    private Long addressId;
    private BigDecimal price;
    private Long exp;


}
