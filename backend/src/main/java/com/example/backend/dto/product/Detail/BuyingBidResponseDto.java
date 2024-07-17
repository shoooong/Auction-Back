package com.example.backend.dto.product.Detail;

import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal productBuyPrice;   // 즉시 구매가
    private BigDecimal productSalePrice;  // 즉시 판매가

    @Override
    public String toString() {
        return "BuyingBidResponseDto{" +
                "productBuyPrice=" + productBuyPrice +
                ", productSalePrice=" + productSalePrice +
                '}';
    }
}
