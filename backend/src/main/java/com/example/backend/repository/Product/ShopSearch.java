package com.example.backend.repository.Product;

import com.example.backend.dto.product.AllProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ShopSearch {
    Slice<AllProductDto> getKeywordSearch(String keyword, Pageable pageable);
}
