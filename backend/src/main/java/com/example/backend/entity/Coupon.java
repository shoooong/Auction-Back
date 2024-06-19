package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false, length = 100)
    private String couponTitle;

    @Column(nullable = false)
    private int couponQuantity;

    @Column(nullable = false)
    private int field;

    @Column(nullable = false, length = 1000)
    private String couponCode;

    @Column(nullable = false)
    private LocalDate creDate;

    @Column(nullable = false)
    private int exp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private int amount;

    // Enum Type
    public enum DiscountType {
        PERCENT, FIXED
    }
}
