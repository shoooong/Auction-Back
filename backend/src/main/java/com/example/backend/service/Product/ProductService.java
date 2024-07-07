package com.example.backend.service.Product;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.dto.product.Detail.RecentlyPriceDto;
import com.example.backend.entity.Product;

import java.util.List;

public interface ProductService {

    // 상품 카테고리에 따라 상품 정보 조회 (소분류)
    List<ProductResponseDto> selectCategoryValue(String subDepartment);

    // 상세 상품 기본 정보 조회
    BasicInformationDto basicInformation(String modelNum);

    RecentlyPriceDto selectRecentlyPrice(String modelNum);
}