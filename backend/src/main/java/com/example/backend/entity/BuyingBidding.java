package com.example.backend.entity;


import com.example.backend.entity.enumData.BinddingStatus;
import jakarta.persistence.*;
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

    private Long buyingPrice;

    private LocalDateTime buyingBiddingTime;

    @Enumerated(EnumType.STRING)
    private BinddingStatus binddingStatus;

}
