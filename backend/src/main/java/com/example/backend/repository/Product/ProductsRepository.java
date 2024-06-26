package com.example.backend.repository.Product;

import com.example.backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, Long>, ProductSearch {

    List<Products> findByModelNum(String modelNum);
}
