package com.example.backend.dto.orders;

import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.enumData.OrderStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Long biddingId;
    private OrderStatus orderStatus;
    private Long orderPrice;
    private LocalDateTime orderDate;

}
