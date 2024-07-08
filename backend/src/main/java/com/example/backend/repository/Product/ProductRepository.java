package com.example.backend.repository.Product;


import com.example.backend.dto.mypage.main.ProductDetailsDto;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, AdminProduct, ProductSearch {
    //상품상태에 따른 상품 찾기
    List<Product> findByProductStatus(ProductStatus productStatus);

    //상품아이디와 상품상태에 따른 상품 찾기
    Optional<Product> findByProductIdAndProductStatus(Long productId, ProductStatus productStatus);

    // 상품 모델번호에 따른 1개의 정보만 가져오기 - 모델번호가 똑같다는 것은 같은 상품이라는 것
    Optional<Product> findFirstByModelNum(String modelNum);

    // 최근에 실행시킨 서버 날짜 가져오기
    Optional<Product> findFirstByModelNumOrderByLatestDateDesc(String modelNum);

    // 거래가 새롭게 체결된 날짜를 업데이트
    Optional<Product> findProductsByProductId(Long productId);

    // 이전 체결가 및 변동률이 없을 경우 0으로 초기화
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.previousPrice = 0L, p.previousPercentage = 0.0 WHERE p.productId = :productId")
    void resetPreviousPrice(Long productId);

    // 신규 체결가 있을때 기존 신규 체결가 -> 신규 이전 체결가
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.previousPrice = :previousContractPrice WHERE p.productId = :recentlyProductId")
    void updatePreviousPrice(@Param("recentlyProductId") Long recentlyProductId, @Param("previousContractPrice") Long previousContractPrice);

    // 해당 Id의 변동률 계산
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.previousPercentage = :changePercentage WHERE p.productId = :recentlyProductId")
    void updateRecentlyContractPercentage(@Param("recentlyProductId") Long recentlyProductId, @Param("changePercentage") Double changePercentage);

    // 최근 체결 상품의 최근 체결 가격 저장
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.latestPrice = :latestPrice WHERE p.productId = :productId")
    void updateLatestPrice(@Param("productId") Long productId, @Param("latestPrice") Long latestPrice);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.differenceContract = :differenceContract WHERE p.productId = :productId")
    void updateDifferenceContract(@Param("productId") Long productId, @Param("differenceContract") Long differenceContract);


    // TODO: QueryDSL로 변경
    // 회원의 관심상품 productIdList로 상품 상세 정보 조회
    @Query("SELECT new com.example.backend.dto.mypage.main.ProductDetailsDto(p.productId, p.productImg, p.productBrand, p.productName, p.modelNum) " +
            "FROM Product p WHERE p.productId IN :productIdList")
    List<ProductDetailsDto> findProductsDetails(List<Long> productIdList);

    // 각 productId에 해당하는 modelNum 조회 후, 같은 modelNum을 가진 모든 productId 조회
    @Query("SELECT DISTINCT p2.productId FROM Product p " +
            "JOIN Product p2 ON p.modelNum = p2.modelNum " +
            "WHERE p.productId IN :productIdList")
    List<Long> findProductIdsByModelNum(List<Long> productIdList);

    @Query("SELECT p.productId, p.modelNum " +
            "FROM Product p WHERE p.productId IN :productIdList")
    List<Object[]> findProductIdAndModelNum(List<Long> productIdList);


}
