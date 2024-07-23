package com.example.backend.repository.Product;

import com.example.backend.dto.admin.AdminProductDto;
import com.example.backend.dto.admin.AdminProductRespDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminProduct {

    //대분류 소분류에 따른 상품 조회
    List<AdminProductDto> getProductsByDepartment(@Param("mainDepartment") String mainDepartment,@Param("subDepartment") String subDepartment);

    //싱품 상세 조회
    List<AdminProductRespDto> getDetailedProduct(@Param("modelNum") String modelNum, @Param("productSize") String productSize);
}
