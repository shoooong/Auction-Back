package com.example.backend.service.requestproduct;

import com.example.backend.dto.product.RequestProductDto;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.repository.Product.RequestProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestProductServiceImpl implements RequestProductService {

    @Autowired
    private RequestProductRepository requestProductRepository;

    @Override
    public void createRequestProduct(RequestProductDto requestProductDto) {

        requestProductDto.setProductStatus(ProductStatus.REQUEST);

        Product product = Product.builder()
                .productImg(requestProductDto.getProductImg())
                .productBrand(requestProductDto.getProductBrand())
                .productName(requestProductDto.getProductName())
                .originalPrice(requestProductDto.getOriginalPrice())
                .mainDepartment(requestProductDto.getMainDepartment())
                .subDepartment(requestProductDto.getSubDepartment())
                .productSize(requestProductDto.getProductSize())
                .productStatus(requestProductDto.getProductStatus())
                .build();

        requestProductRepository.save(product);
    }
}
