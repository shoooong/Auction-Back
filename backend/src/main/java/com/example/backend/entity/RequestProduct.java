package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class RequestProduct {

    //미등록 상품 요청
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false, length = 100)
    private String requestTitle;

    @Column(nullable = false)
    private LocalDate requestDate;

    @Column(nullable = false, length = 50)
    private String requestProductImage;

    private boolean approval;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal requestPrice;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal openPrice;

    @Column(nullable = false, length = 50)
    private String requestProductName;

    @Column(nullable = false, length = 50)
    private String requestProductSize;

    @Column(nullable = false, length = 50)
    private String requestProductColor;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "categoryId")
    private Category category;
}
