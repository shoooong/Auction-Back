package com.example.backend.repository.Product;


import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.dto.product.Detail.BuyingHopeDto;
import com.example.backend.dto.product.Detail.SalesBiddingDto;
import com.example.backend.dto.product.Detail.SalesHopeDto;
import com.example.backend.entity.Product;

import java.util.List;

public interface ProductSearch {

    // 소분류 상품 전체 보기
    List<Product>  subProductInfo(String subDepartment);
    //
    // 상품 최고, 최저 입찰 희망가격 조회
    BasicInformationDto searchProductPrice(String modelNum);

    // 해당 상품의 기존 체결가가 있는지 확인
    List<SalesBiddingDto> recentlyTransaction(String modelNum);

    List<SalesHopeDto> SalesHopeInfo(String modelNum);

    List<BuyingHopeDto> BuyingHopeInfo(String modelNum);
}
