package com.example.backend.dto.product.Detail;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesHopeDto {
    private String productSize;
    private Long salesBiddingPrice;
    private int salesQuantity;
}
