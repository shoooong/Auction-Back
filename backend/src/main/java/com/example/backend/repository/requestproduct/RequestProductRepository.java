package com.example.backend.repository.requestproduct;

import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductStatus(ProductStatus productStatus);
    Optional<Product> findByProductIdAndProductStatus(Long productId, ProductStatus productStatus);
}
