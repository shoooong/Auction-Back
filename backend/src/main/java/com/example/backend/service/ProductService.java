package com.example.backend.service;

import com.example.backend.dto.product.ProductResponseDTO;
import com.example.backend.entity.Products;

import java.util.List;

public interface ProductService {

    List<Products> selectProductById(String categoryType);

    List<ProductResponseDTO> selectCategoryName(String categoryName);
}
