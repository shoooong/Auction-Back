package com.example.backend.service.Product;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.BasicInformationDto;

import java.util.List;

public interface ProductService {

    // 상품 카테고리에 따라 상품 정보 조회 (소분류)
    List<ProductResponseDto> selectCategoryValue(String subDepartment);

    // 상세 상품 기본 정보 조회
    BasicInformationDto basicInformation(String modelNum);

    BasicInformationDto selectProductDetailPrice(String modelNum);





    // JPA 사용 버전 : 상품 상세 정보 조회
//    List<ProductResponseDTO> detailProductSelect(String modelNum);
//    OnlyProductResponseDTO detailProductSelect(OnlyProductRequestDTO onlyProductRequestDTO);
//
//    // 상품 상세에서 구매, 판매 가격 구하기
//    PriceResponseDTO selectProductPrice(OnlyProductRequestDTO onlyProductRequestDTO);
}