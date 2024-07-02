package com.example.backend.service;


import com.example.backend.dto.admin.AdminProductDto;
import com.example.backend.dto.admin.AdminRespDto;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class AdminService {

    private final ProductRepository productRepository;

    //요청상품 다건 조회
    public AdminRespDto.ReqProductsRespDto reqProducts(){
        List<Product> products = productRepository.findByProductStatus(ProductStatus.REQUEST);

        return new AdminRespDto.ReqProductsRespDto(products);

    }

    // 요청상품 단건 조회
    public AdminRespDto.ReqProductRespDto reqProduct(Long productId) {
        //productId로 상품찾기
        Optional<Product> reqProduct = productRepository.findById(productId);
        Product result = reqProduct.orElseThrow();

        return new AdminRespDto.ReqProductRespDto(result);


    }

    //요청상품 판매상품으로 등록
    @Transactional
    public AdminRespDto.RegProductRespDto acceptRequest(Long productId) {
        Optional<Product> result = productRepository.findByProductIdAndProductStatus(productId,ProductStatus.REQUEST);
        Product request = result.orElseThrow();

        //모델번호 + 사이즈로 중복값이 있는지 판별
        List<Product> registeredProducts = productRepository.findByProductStatus(ProductStatus.REGISTERED);
        Product finalRequest = request;
        boolean isDuplicate = registeredProducts.stream().anyMatch(registered ->
                finalRequest.getModelNum().equals(registered.getModelNum()) &&
                        finalRequest.getProductSize().equals(registered.getProductSize())
        );
        if (isDuplicate) {
            // 중복이면
            throw new RuntimeException("이미 기존 상품에 등록되어 있습니다.");
        } else {

            finalRequest.changeProductStatus(ProductStatus.REGISTERED);
            return new AdminRespDto.RegProductRespDto(finalRequest);
        }
    }

    //판매상품 관리(카테고리별)
    public List<AdminProductDto> getProducts(String mainDepartment, String subDepartment) {
        List<AdminProductDto> adminProductDto = productRepository.getProductsByDepartment(mainDepartment, subDepartment);

        log.info("mainDepartment {} subDepartment from 서비스로 반환!!!!!ㅅㅂ", mainDepartment, subDepartment);
        return adminProductDto;



    }


//    public AdminRespDto.AdminProductsRespDto findProductByDepartment(String mainDepartment, String subDepartment) {
//
//        log.info("mainDepart{} subDepartment{}",mainDepartment,subDepartment);
//        List<Product> products = productRepository.findProductByDepartment(mainDepartment,subDepartment);
//
//        return new AdminRespDto.AdminProductsRespDto(products);
//    }
}
