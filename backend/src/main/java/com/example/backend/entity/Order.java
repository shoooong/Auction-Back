package com.example.backend.entity;

import com.example.backend.entity.enumData.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Long orderPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCouponId")
    private UserCoupon userCouponId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;


}
