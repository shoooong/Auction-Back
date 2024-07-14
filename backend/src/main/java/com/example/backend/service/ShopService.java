package com.example.backend.service;

import com.example.backend.dto.product.TotalProductDto;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.Product.ShopProductImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ShopService {

    private final ProductRepository productRepository;

    public Slice<TotalProductDto> getTotalProduct(Pageable pageable){

        return productRepository.allProduct(pageable);
    }
}
