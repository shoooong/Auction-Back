package com.example.backend.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class AdminProductDto {
    private String productName;
    private String modelNum;
    private String productBrand;




    public AdminProductDto(String productName, String modelNum, String productBrand) {
        this.productName = productName;
        this.modelNum = modelNum;
        this.productBrand = productBrand;
    }
}
