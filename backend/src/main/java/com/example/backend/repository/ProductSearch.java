package com.example.backend.repository;

import com.example.backend.entity.Products;

import java.util.List;

public interface ProductSearch {
    void updateProducts(Long productId, String categoryName);
    List<Products> selectProductInfo(String categoryName);
}
