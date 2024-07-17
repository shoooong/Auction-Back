package com.example.backend.dto.mypage.saleHistory;

import com.example.backend.entity.enumData.SalesStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class SaleDetailsDto {

    private String productImg;
    private String productName;
    private String productSize;

    private BigDecimal saleBiddingPrice;
    private SalesStatus salesStatus;


    public SaleDetailsDto(String productImg, String productName, String productSize, BigDecimal saleBiddingPrice, SalesStatus salesStatus) {
        this.productImg = productImg;
        this.productName = productName;
        this.productSize = productSize;
        this.saleBiddingPrice = saleBiddingPrice;
        this.salesStatus = salesStatus;
    }
}
