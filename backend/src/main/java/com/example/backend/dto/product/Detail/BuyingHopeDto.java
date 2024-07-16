package com.example.backend.dto.product.Detail;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyingHopeDto {
    private String productSize;
    private Long buyingBiddingPrice;
    private int buyingQuantity;
}
