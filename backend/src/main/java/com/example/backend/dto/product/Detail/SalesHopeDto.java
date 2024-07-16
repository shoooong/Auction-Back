package com.example.backend.dto.product.Detail;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesHopeDto {
    private String productSize;
    private BigDecimal salesBiddingPrice;
    private int salesQuantity;
}
