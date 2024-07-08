package com.example.backend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlyProductResponseDto {

    private Long productId;
    private String productBrand;
    private String productName;
    private String modelNum;
    private BigDecimal originalPrice;
    private int productLike;
    private String categoryName;
    private String categoryType;

    private BigDecimal expectBuyPrice;
    private BigDecimal expectSellPrice;
}
