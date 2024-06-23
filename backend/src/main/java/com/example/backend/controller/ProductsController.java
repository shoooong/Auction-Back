package com.example.backend.controller;

import com.example.backend.dto.product.ProductResponseDTO;
import com.example.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductResponseDTO> products(@RequestParam(name = "categoryName", required = false, defaultValue = "하의") String categoryName) {
        List<ProductResponseDTO> products = productService.selectCategoryName(categoryName);
        log.info("상품 정보 : {}", products);
        return products;
    }

//    @GetMapping("/products")
//    public List<Products> products2(@RequestParam(name = "categoryType", required = false, defaultValue = "패션") String categoryType) {
//        List<Products> products = productService.selectCategoryType(categoryType);
//        log.info("상품 정보 : {}", products);
//        log.info("----------------------------------");
//        return products;
//    }
}