package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BidKind bidKind;

    @Column(nullable = false)
    private int bidPrice;

    @Column(nullable = false)
    private LocalDateTime bidStartDate;

    @Column(nullable = false)
    private LocalDateTime bidModifyDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BidStatus bidStatus;

    @Column(nullable = false)
    private LocalDateTime bidEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizeId")
    private Size size;

    public enum BidKind {
        BUY, SELL
    }

    public enum BidStatus {
        INSPECTION, PROGRESS, COMPLETE
    }
}
