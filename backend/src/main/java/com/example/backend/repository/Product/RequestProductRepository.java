package com.example.backend.repository.Product;

import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestProductRepository extends JpaRepository<Product, Long> {
}
