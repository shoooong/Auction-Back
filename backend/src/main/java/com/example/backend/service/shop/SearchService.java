package com.example.backend.service.shop;

import com.example.backend.dto.product.AllProductDto;
import com.example.backend.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class SearchService {
    private final ProductRepository productRepository;

    public Slice<AllProductDto> getSearch(String keyword, Pageable pageable) {

        return productRepository.getKeywordSearch(keyword, pageable);
    }
}
