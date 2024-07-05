package com.example.backend.dto.mypage.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsDetailsDto {

    private String productImg;
    private String productBrand;
    private String productName;
    private String modelNum;
}
