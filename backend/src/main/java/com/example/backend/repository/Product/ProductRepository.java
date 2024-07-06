package com.example.backend.repository.Product;


import com.example.backend.dto.mypage.main.ProductsDetailsDto;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, AdminProduct, ProductSearch {
    //상품상태에 따른 상품 찾기
    List<Product> findByProductStatus(ProductStatus productStatus);

    //상품아이디와 상품상태에 따른 상품 찾기
    Optional<Product> findByProductIdAndProductStatus(Long productId, ProductStatus productStatus);

    // 상품 모델번호에 따른 1개의 정보만 가져오기 - 모델번호가 똑같다는 것은 같은 상품이라는 것
    Optional<Product> findFirstByModelNum(String modelNum);

    // 해당 상품 ID가 가지고 있는 최근 체결 가격 가져오기
    Optional<Product> findFirstByModelNumOrderByLatestDateDesc(String modelNum);


    // TODO: QueryDSL로 변경
    // 회원의 관심상품 productIdList로 상품 상세 정보 조회
    @Query("SELECT new com.example.backend.dto.mypage.main.ProductsDetailsDto(p.productId, p.productImg, p.productBrand, p.productName, p.modelNum) " +
            "FROM Product p WHERE p.productId IN :productIdList")
    List<ProductsDetailsDto> findProductsDetails(List<Long> productIdList);

    // 각 productId에 해당하는 modelNum 조회 후, 같은 modelNum을 가진 모든 productId 조회
    @Query("SELECT DISTINCT p2.productId FROM Product p " +
            "JOIN Product p2 ON p.modelNum = p2.modelNum " +
            "WHERE p.productId IN :productIdList")
    List<Long> findProductIdsByModelNum(List<Long> productIdList);

    @Query("SELECT p.productId, p.modelNum " +
            "FROM Product p WHERE p.productId IN :productIdList")
    List<Object[]> findProductIdAndModelNum(List<Long> productIdList);


}
