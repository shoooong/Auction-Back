package com.example.backend.dto.orders;

import com.example.backend.entity.enumData.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailsDTO {

    private String productImg;
    private String productName;
    private String productSize;

    private Long orderPrice;
    private OrderStatus orderStatus;


    public OrderDetailsDTO(String productImg, String productName, String productSize, Long orderPrice, OrderStatus orderStatus) {
        this.productImg = productImg;
        this.productName = productName;
        this.productSize = productSize;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }
}
