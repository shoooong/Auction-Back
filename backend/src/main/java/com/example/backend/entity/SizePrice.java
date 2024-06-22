package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SizePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizePriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizeId", nullable = false)
    @ToString.Exclude
    private Size size;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @Column(nullable = false)
    private int quantity;
}