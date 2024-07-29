package com.example.backend.dto.orders;

import com.example.backend.dto.admin.AdminRespDto.ReqProductsRespDto.ProductDto;
import com.example.backend.dto.mypage.addressSettings.AddressDto;
import com.example.backend.dto.requestproduct.RequestProductDto;
import com.example.backend.entity.Address;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.OrderStatus;
import java.math.BigDecimal;
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
    private OrderProductDto product;
    private BuyingBiddingDto biddingBidding;
    private SalesBiddingDto salesBidding;
    private AddressDto address;
    private OrderStatus orderStatus;
    private BigDecimal orderPrice;
    private LocalDateTime orderDate;

}
