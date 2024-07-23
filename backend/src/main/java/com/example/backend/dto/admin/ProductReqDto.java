package com.example.backend.dto.admin;

import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReqDto {

    private String productImg;
    private String productBrand;
    private String modelNum;
    private String productName;
    private BigDecimal originalPrice;
    private String productSize;
    private ProductStatus productStatus;

}
