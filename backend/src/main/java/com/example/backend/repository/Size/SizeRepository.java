package com.example.backend.repository.Size;

import com.example.backend.entity.Product;
import com.example.backend.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findByProduct(Product product);
}
