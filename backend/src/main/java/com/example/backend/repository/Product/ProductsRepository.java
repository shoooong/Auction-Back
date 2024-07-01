package com.example.backend.repository.Product;

import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Product, Long>, ProductSearch {

    Product findFirstByModelNum(String modelNum);
}
