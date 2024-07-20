package com.example.backend.entity;


import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.SalesStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class BuyingBidding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyingBiddingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private BigDecimal buyingBiddingPrice;

    private int buyingQuantity;

    private LocalDateTime buyingBiddingTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private BiddingStatus biddingStatus;

    // 구매 입찰 상태 변경

    public void changeBiddingStatus(BiddingStatus biddingStatus){
        this.biddingStatus = biddingStatus;
    }

}
