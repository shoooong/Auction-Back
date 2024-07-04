package com.example.backend.repository.Product;


import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
