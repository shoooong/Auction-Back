package com.example.backend.dto.orders;

import com.example.backend.entity.Product;
import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {
    private String modelNum;
    private Long productId;
    private String productBrand;
    private String productName;
    private String subDepartment;
    private String productImg;
    private String productSize;

    public static OrderProductDto fromEntity(Product product) {
        return OrderProductDto.builder()
            .modelNum(product.getModelNum())
            .productId(product.getProductId())
            .productBrand(product.getProductBrand())
            .productName(product.getProductName())
            .subDepartment(product.getSubDepartment())
            .productImg(product.getProductImg())
            .productSize(product.getProductSize())
            .build();
    }
}
