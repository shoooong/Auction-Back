package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<ProductsContractListDto> contractInfoList; // 체결 내역
    private List<SalesHopeDto> salesHopeList;   // 구매 희망 가격
    private List<BuyingHopeDto> buyingHopeList; // 판매 희망 가격

    private List<PhotoReviewDto> photoReviewList;   // 해당 상품의 스타일 리뷰

}
