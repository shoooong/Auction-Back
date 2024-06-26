package com.example.backend.service;

import com.example.backend.dto.product.ProductRequestDTO;
import com.example.backend.dto.product.ProductResponseDTO;
import com.example.backend.entity.Products;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> detailProductSelect(String modelNum);

    ProductResponseDTO detailProductInfo(String modelNum);

    List<ProductResponseDTO> selectCategoryValue(String categoryValue);
}
