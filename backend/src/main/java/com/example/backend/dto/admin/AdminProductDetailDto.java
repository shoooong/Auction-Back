package com.example.backend.dto.admin;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDetailDto {

    private Long productId;
    private String productImg;
    private String productBrand;
    private String productName;
    private String modelNum;
    private Long originalPrice;
    private int productQuantity;
    private String productSize;

}
