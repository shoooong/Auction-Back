package com.example.backend.dto.product.Detail;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupBySalesDto {
    private String productImg;
    private String productName;
    private String modelNum;
    private String productSize;
    private BigDecimal productMaxPrice;
    private Long productId;
}
