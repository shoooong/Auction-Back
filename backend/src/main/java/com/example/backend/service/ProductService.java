package com.example.backend.service;

import com.example.backend.entity.Products;

import java.util.List;

public interface ProductService {

    List<Products> selectCategotyType(String productType);

}
