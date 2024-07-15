package com.example.backend.dto.product.Detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long productId;
    private String productImg;
    private String productBrand;
    private String modelNum;
    private String productName;
    private Long originalPrice;
    private int productLike;

    private Long buyingBiddingPrice;
    private Long salesBiddingPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime latestDate;
    private Long latestPrice;
    private Long previousPrice;
    private Double changePercentage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime recentlyContractDate;
    private Long differenceContract;

    private List<ProductsContractListDto> contractInfoList; // 체결 내역
    private List<SalesHopeDto> salesHopeList;   // 구매 희망 가격
    private List<BuyingHopeDto> buyingHopeList; // 판매 희망 가격

    private List<PhotoReviewDto> photoReviewList;   // 해당 상품의 스타일 리뷰

    private List<GroupByBuyingDto> groupByBuyingList;
    private List<GroupBySalesDto> groupBySalesList;

    private List<AveragePriceResponseDto> averagePriceResponseList;

}
