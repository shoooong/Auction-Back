package com.example.backend.dto.product.Detail;

import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.SalesStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertBidDto {
    private Long userId;
    private Long productId;
    private String modelNum;
    private String size;
    private String type;
    private BigDecimal price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime duration;

    public BuyingBidding toBuyingBidding(Users user, Product product) {
        return BuyingBidding.builder()
                .user(user)
                .product(product)
                .buyingBiddingPrice(this.price)
                .buyingQuantity(1)
                .buyingBiddingTime(this.duration)
                .biddingStatus(BiddingStatus.PROCESS)
                .build();
    }

    public SalesBidding toSalesBidding(Users user, Product product) {
        return SalesBidding.builder()
                .user(user)
                .product(product)
//                .salesBiddingPrice(this.price)
                .salesQuantity(1)
                .salesBiddingTime(this.duration)
                .salesStatus(SalesStatus.PROCESS)
                .build();
    }
}
