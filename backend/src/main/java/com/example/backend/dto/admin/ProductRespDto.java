package com.example.backend.dto.admin;

import lombok.Getter;
import lombok.Setter;

//범수
//대분류별 상품 조회
@Getter
@Setter
public class ProductRespDto {
    private Long productId;
    private String brand;
    private String productName;
    private String modelNum;
    private String productImg;
    private String mainDepartment;
    private String subDepartment;
    private String productSize;

    private Long 최저가;

    public ProductRespDto(Long productId, String brand, String productName, String modelNum, String productImg, String mainDepartment, String subDepartment,String productSize, Long 최저가) {
        this.productId = productId;
        this.brand = brand;
        this.productName = productName;
        this.modelNum = modelNum;
        this.productImg = productImg;
        this.mainDepartment = mainDepartment;
        this.subDepartment = subDepartment;
        this.productSize = productSize;
        this.최저가 = 최저가;
    }
}
