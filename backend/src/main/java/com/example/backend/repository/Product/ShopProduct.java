package com.example.backend.repository.Product;

import com.example.backend.dto.product.TotalProductDto;
import com.example.backend.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ShopProduct{
    Slice<TotalProductDto> allProduct(Pageable pageable);
}
