package com.example.backend.dto.admin;

import lombok.Getter;
import lombok.Setter;

//범수
//대분류별 상품 조회
@Getter
@Setter
public class ProductRespDto {

    private String brand;
    private String productName;
    private String modelNum;
    private String productImg;
    private String mainDepartment;

    private Long 최저가;

    public ProductRespDto( String brand, String productName, String modelNum, String productImg, String mainDepartment, Long 최저가) {
        this.brand = brand;
        this.productName = productName;
        this.modelNum = modelNum;
        this.productImg = productImg;
        this.mainDepartment = mainDepartment;
        this.최저가 = 최저가;
    }
}
