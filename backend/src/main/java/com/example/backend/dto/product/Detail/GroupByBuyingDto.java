package com.example.backend.dto.product.Detail;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupByBuyingDto {
    private Long buyProductId;
    private String productImg;
    private String productName;
    private String modelNum;
    private String productSize;
    private BigDecimal buyingBiddingPrice;
    private Long productId;
}