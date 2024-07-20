package com.example.backend.entity;

import com.example.backend.entity.enumData.OrderStatus;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
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
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    private BigDecimal orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyingBiddingId")
    private BuyingBidding buyingBidding;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesBiddingId")
    private SalesBidding salesBidding;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponID")
    private Coupon coupon;
}
