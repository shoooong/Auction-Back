package com.example.backend.service.requestproduct;

import com.example.backend.dto.requestproduct.RequestProductDto;
import com.example.backend.dto.requestproduct.RequestProductListDto;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface RequestProductService {
    void createRequestProduct(RequestProductDto requestProductDto);
    List<RequestProductListDto> getRequestProducts();
    RequestProductDto getRequestProductById(Long productId);
}
