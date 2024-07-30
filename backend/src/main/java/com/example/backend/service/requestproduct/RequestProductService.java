package com.example.backend.service.requestproduct;

import com.example.backend.dto.requestproduct.RequestProductDto;
import com.example.backend.dto.requestproduct.RequestProductListDto;
import java.util.List;

public interface RequestProductService {
    void createRequestProduct(RequestProductDto requestProductDto);
    List<RequestProductListDto> getRequestProducts();
    RequestProductDto getRequestProductById(Long productId);
}
