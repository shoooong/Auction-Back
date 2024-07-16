package com.example.backend.dto.product.Detail;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyingBidResponseDto {
    private String productImg;
    private String productName;
    private String productSize;
    private Long productBuyPrice;   // 즉시 구매가
    private Long productSalePrice;  // 즉시 판매가
}
