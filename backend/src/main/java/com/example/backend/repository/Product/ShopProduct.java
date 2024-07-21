package com.example.backend.repository.Product;

import com.example.backend.dto.product.AllProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ShopProduct{
    Slice<AllProductDto> allProduct(Pageable pageable);

    Slice<AllProductDto> getProductsByMainDepartment(Pageable pageable, List<String> mainDepartment);

    Slice<AllProductDto> getProductsBySubDepartment(Pageable pageable, List<String> subDepartment);
}
