package com.example.backend.entity;


import com.example.backend.entity.enumData.BinddingStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class BuyingBidding extends BaseEntity {

    @Id
    private Long buyingBiddingId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private Long buyingBiddingPrice;

    private int buyingQuantity;

    private Long buyingPrice;

    @Enumerated(EnumType.STRING)
    private BinddingStatus binddingStatus;

}
