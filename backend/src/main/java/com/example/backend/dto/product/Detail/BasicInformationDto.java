package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInformationDto {
    private Long productId;
    private String productImg;
    private String productBrand;
    private String modelNum;
    private String productName;
    private Long originalPrice;
    private int productLike;

    private Long buyingBiddingPrice;
    private Long salesBiddingPrice;
}
