package com.example.backend.entity;

import com.example.backend.entity.enumData.SalesStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class SalesBidding extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesBiddingId;

    private Long salesBiddingPrice;

    private int salesQuantity;

    private Long salesPrice;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user ;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    private String saleseBiddingTime;

}
