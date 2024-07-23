package com.example.backend.dto.admin;

import lombok.*;

import java.math.BigDecimal;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDetailDto {

    private Long productId;
    private String productImg;
    private String productBrand;
    private String productName;
    private String modelNum;
    private BigDecimal originalPrice;
    private int productQuantity;
    private String productSize;

}
