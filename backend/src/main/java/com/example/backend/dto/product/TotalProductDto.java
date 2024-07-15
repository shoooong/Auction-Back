package com.example.backend.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
public class TotalProductDto {

    private String modelNum;
    private Long productId;
    private String productBrand;
    private String productName;
    private String subDepartment;
    private String productImg;

    // 즉시 구매가를 위함
    private Long buyingBiddingPrice;

}
