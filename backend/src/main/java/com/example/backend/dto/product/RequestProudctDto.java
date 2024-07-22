package com.example.backend.dto.product;

import com.example.backend.entity.enumData.ProductStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestProudctDto {

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
