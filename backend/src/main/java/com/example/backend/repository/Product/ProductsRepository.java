package com.example.backend.repository.Product;

import com.example.backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, Long>, ProductSearch {

    Products findFirstByModelNum(String modelNum);
}
