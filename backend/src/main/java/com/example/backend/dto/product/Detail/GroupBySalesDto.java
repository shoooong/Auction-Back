package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupBySalesDto {
    private String productImg;
    private String productName;
    private String modelNum;
    private String productSize;
    private Long productMaxPrice;
}
