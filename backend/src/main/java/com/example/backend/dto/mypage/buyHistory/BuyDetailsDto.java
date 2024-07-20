package com.example.backend.dto.mypage.buyHistory;

import com.example.backend.entity.enumData.OrderStatus;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuyDetailsDto {

    private String productImg;
    private String productName;
    private String productSize;

    private BigDecimal orderPrice;
    private OrderStatus orderStatus;

    public BuyDetailsDto(String productImg, String productName, String productSize, BigDecimal orderPrice, OrderStatus orderStatus) {
        this.productImg = productImg;
        this.productName = productName;
        this.productSize = productSize;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }
}
