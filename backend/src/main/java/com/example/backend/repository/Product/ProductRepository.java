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

}
