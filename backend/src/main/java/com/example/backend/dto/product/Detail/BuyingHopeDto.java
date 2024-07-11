package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyingHopeDto {
    private String productSize;
    private Long buyingBiddingPrice;
    private int buyingQuantity;
}
