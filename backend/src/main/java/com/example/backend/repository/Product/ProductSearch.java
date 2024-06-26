package com.example.backend.repository.Product;

import com.example.backend.dto.product.ProductRequestDTO;
import com.example.backend.entity.Products;

import java.util.List;

public interface ProductSearch {
    List<Products> allProductInfo(String categoryValue);

    Products detailProductInfo(String modelNum);

    Products searchProductPrice(ProductRequestDTO productRequestDTO);
}
