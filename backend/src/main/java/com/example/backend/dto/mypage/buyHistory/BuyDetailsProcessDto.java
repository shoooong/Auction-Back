package com.example.backend.dto.mypage.buyHistory;

import com.example.backend.entity.enumData.BiddingStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class BuyDetailsProcessDto {

    private String productImg;
    private String productName;
    private String productSize;

    private BigDecimal buyingBiddingPrice;
    private BiddingStatus biddingStatus;

    public BuyDetailsProcessDto(String productImg, String productName, String productSize, BigDecimal buyingBiddingPrice, BiddingStatus biddingStatus) {
        this.productImg = productImg;
        this.productName = productName;
        this.productSize = productSize;
        this.buyingBiddingPrice = buyingBiddingPrice;
        this.biddingStatus = biddingStatus;
    }
}
