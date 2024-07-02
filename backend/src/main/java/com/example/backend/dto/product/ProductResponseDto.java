package com.example.backend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private String productImg;
    private String productBrand;
    private String productName;
    private int productLike;
    private String modelNum;
    private List<BuyingDto> buyingDto;
}
