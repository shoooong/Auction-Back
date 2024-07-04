package com.example.backend.entity;

import com.example.backend.entity.enumData.DiscountType;
import com.example.backend.entity.enumData.SalesStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Coupon extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false, length = 150)
    private String couponTitle;

    @Column(nullable = false)
    private Long couponQuantity;

    @Column(nullable = false)
    private Long maxQuantity;

    @Column(nullable = false, length = 1000)
    private String couponCode;

    @Column(nullable = false)
    private Long expDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Long amount;
}
