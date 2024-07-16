package com.example.backend.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;
    private String productImg;
    private String productBrand;
    private String productName;
    private int productLike;
    private String modelNum;
    private BigDecimal ProductMinPrice;
    private List<BuyingDto> buyingDto;
}
