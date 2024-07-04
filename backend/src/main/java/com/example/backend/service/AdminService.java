package com.example.backend.service;


import com.example.backend.dto.admin.*;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.entity.enumData.SalesStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final SalesBiddingRepository salesBiddingRepository;

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

        log.info("productId" + adminProductDto.get(0).getProductId());
        return adminProductDto;

    }

    //상품상세 조회 + 판매입찰 + 구매입찰 정보
    public List<AdminProductRespDto> getDetailProduct(String modelNum, String productSize) {

        log.info("modelNum{} productSize{}", modelNum, productSize);
        /*
        1. modelNum 기준으로 중복없이 상품 상세 정보 조회,
        2. 상품 사이즈별로 수량,입찰(판매, 구매) 정보 같이 가져오기
        * */
        List<AdminProductRespDto> detailedProduct = productRepository.getDetailedProduct(modelNum,productSize);


        return detailedProduct;

    }

    //검수 승인 처리
    @Transactional
    public void acceptSales(Long salesBiddingId) {

        //해당 id의 판매입찰 정보 찾기
        Optional<SalesBidding> salesBidding = salesBiddingRepository.findById(salesBiddingId);
        SalesBidding result = salesBidding.orElseThrow();

        //판매입찰 상태 검수 -> 판매중으로 변경
        result.chageSalesStatus(SalesStatus.PROCESS);

    }


}
