package com.example.backend.dto.product.Detail;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyingHopeDto {
    private String productSize;
    private BigDecimal buyingBiddingPrice;
    private int buyingQuantity;
}
