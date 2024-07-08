package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime latestDate;
    private Long latestPrice;
    private Long previousPrice;
    private Double changePercentage;
    private LocalDateTime recentlyContractDate;
    private Long differenceContract;

}
