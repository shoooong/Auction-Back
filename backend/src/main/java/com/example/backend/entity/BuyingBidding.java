package com.example.backend.entity;


import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.SalesStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class BuyingBidding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyingBiddingId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private Long buyingBiddingPrice;

    private int buyingQuantity;

    private LocalDateTime buyingBiddingTime;

    @Enumerated(EnumType.STRING)
    private BiddingStatus biddingStatus;

    // 구매 입찰 상태 변경

    public void changeBiddingStatus(BiddingStatus biddingStatus){
        this.biddingStatus = biddingStatus;
    }

}
