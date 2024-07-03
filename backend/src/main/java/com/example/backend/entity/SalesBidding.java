package com.example.backend.entity;

import com.example.backend.entity.enumData.SalesStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class SalesBidding extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesBiddingId;

    private Long salesBiddingPrice;

    private int salesQuantity;

    private Long salesPrice;

    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    private LocalDateTime salesBiddingTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    @ToString.Exclude
    private Product product;

}
