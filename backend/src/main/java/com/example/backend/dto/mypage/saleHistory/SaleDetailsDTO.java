package com.example.backend.dto.mypage.saleHistory;

import com.example.backend.entity.enumData.SalesStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaleDetailsDTO {

    private String productImg;
    private String productName;
    private String productSize;

    private Long saleBiddingPrice;
    private SalesStatus salesStatus;


    public SaleDetailsDTO(String productImg, String productName, String productSize, Long saleBiddingPrice, SalesStatus salesStatus) {
        this.productImg = productImg;
        this.productName = productName;
        this.productSize = productSize;
        this.saleBiddingPrice = saleBiddingPrice;
        this.salesStatus = salesStatus;
    }
}
