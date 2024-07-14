package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyingBidResponseDto {
    private String productImg;
    private String productName;
    private String productSize;
    private Long productBuyPrice;   // 즉시 구매가
    private Long productSalePrice;  // 즉시 판매가
}
