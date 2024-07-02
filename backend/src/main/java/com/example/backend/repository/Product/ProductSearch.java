package com.example.backend.repository.Product;


import com.example.backend.dto.product.ProductResponseDto;
import com.example.backend.entity.Product;

import java.util.List;

public interface ProductSearch {

    // 소분류 상품 전체 보기
    List<Product>  subProductInfo(String subDepartment);
//
//    PriceResponseDTO searchProductPrice(String modelNum);
}
