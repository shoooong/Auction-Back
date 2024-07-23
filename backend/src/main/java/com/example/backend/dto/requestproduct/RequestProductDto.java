package com.example.backend.dto.requestproduct;

import com.example.backend.entity.enumData.ProductStatus;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestProductDto {

    private Long productId;
    private String productImg;
    private String productBrand;
    private String productName;
    private BigDecimal originalPrice;
    private String mainDepartment;
    private String subDepartment;
    private String productSize;
    private ProductStatus productStatus;
    private String modelNum;
}
