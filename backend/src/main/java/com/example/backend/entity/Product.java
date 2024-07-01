package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 255)
    private String productImg;

    @Column(nullable = false, length = 50)
    private String productBrand;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, length = 100)
    private String modelNum;

    @Column(nullable = false)
    private Long originalPrice;

    @Column(nullable = false)
    @Builder.Default
    private int productLike = 0;

    @Column(nullable = false, length = 50)
    private String mainDepartment;

    @Column(nullable = false, length = 50)
    private String subDepartment;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false, length = 50)
    private String productSize;



}
