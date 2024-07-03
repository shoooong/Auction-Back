package com.example.backend.controller;

import com.example.backend.dto.product.*;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품(카테고리 소분류) 불러오기
    @GetMapping("/products/{subDepartment}")
    public List<ProductResponseDto> products(@PathVariable String subDepartment) {

        List<ProductResponseDto> products = productService.selectCategoryValue(subDepartment);
        log.info("상품 정보 : {}", products);
        return products;
    }

//    // 해당 상품(상세) 기본 정보 가져오기
//    @GetMapping("/products/details/{modelNum}")
//    public OnlyProductResponseDTO productDetailSelect(@PathVariable String modelNum) {
//        // 상품 기본 정보 가져오기
//        OnlyProductRequestDTO onlyProductRequestDTO = new OnlyProductRequestDTO();
//        onlyProductRequestDTO.setModelNum(modelNum);
//        OnlyProductResponseDTO products = productService.detailProductSelect(onlyProductRequestDTO);
//
//        // 해당 상품 구매, 판매 가격 가져오기
//        PriceResponseDTO priceResponseDTO = productService.selectProductPrice(onlyProductRequestDTO);
//        products.setExpectBuyPrice(priceResponseDTO.getExpectBuyPrice());
//        products.setExpectSellPrice(priceResponseDTO.getExpectSellPrice());
//
//        return products;
//    }
}