package com.example.backend.repository.Product;

import com.example.backend.dto.product.PriceResponseDTO;
import com.example.backend.entity.Products;

import java.util.List;

public interface ProductSearch {

    // 소분류 상품 전체 보기
    List<Products> allProductInfo(String categoryName);

    PriceResponseDTO searchProductPrice(String modelNum);
}
