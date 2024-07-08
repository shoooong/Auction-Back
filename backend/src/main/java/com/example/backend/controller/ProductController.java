package com.example.backend.controller;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.service.Product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

        log.info("subDepartment : " + subDepartment);
        List<ProductResponseDto> products = productService.selectCategoryValue(subDepartment);
        log.info("상품 정보 : {}", products);
        return products;
    }

    // 해당 상품(상세) 기본 정보 가져오기
    @GetMapping("/products/details/{modelNum}")
    public BasicInformationDto productDetailSelect(@PathVariable String modelNum) {

        // (상품의 기본 정보) && (해당 상품의 구매(최저) / 판매(최고)가 조회) && (해당 상품에 대한 최근 체결 정보)
        BasicInformationDto basicInformationDto = productService.basicInformation(modelNum);
        log.info("basicInformationDto : " + basicInformationDto);

        return basicInformationDto;
    }
}