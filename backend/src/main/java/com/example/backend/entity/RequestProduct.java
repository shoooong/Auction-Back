package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class RequestProduct extends BaseEntity {

    //미등록 상품 요청
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false, length = 100)
    private String requestTitle;

    @Column(nullable = false, length = 50)
    private String requestProductImage;

    private boolean approval;

    private String modelNum;

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
    @JoinColumn(name = "categoryId")
    private Category category;


    public void changeApproval(boolean approval) {
        this.approval = approval;
    }
}
