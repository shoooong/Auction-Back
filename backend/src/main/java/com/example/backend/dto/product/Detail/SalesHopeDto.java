package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesHopeDto {
    private String productSize;
    private Long salesBiddingPrice;
    private int salesQuantity;
}
