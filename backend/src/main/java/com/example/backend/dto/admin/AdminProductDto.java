package com.example.backend.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AdminProductDto {

    private Long productId;
    private String productName;
    private String modelNum;
    private String productBrand;
    private String productSize;


    public AdminProductDto(Long productId,String productName, String modelNum, String productBrand, String productSize) {
        this.productId = productId;
        this.productName = productName;
        this.modelNum = modelNum;
        this.productBrand = productBrand;
        this.productSize = productSize;
    }
}
