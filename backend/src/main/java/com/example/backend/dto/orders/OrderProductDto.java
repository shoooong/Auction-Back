package com.example.backend.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
