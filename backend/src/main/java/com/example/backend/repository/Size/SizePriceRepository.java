package com.example.backend.repository.Size;

import com.example.backend.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizePriceRepository extends JpaRepository<SizePrice, Long> {
    List<SizePrice> findBySize(Size size);
}
