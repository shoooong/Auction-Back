package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LuckyDraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long luckyId;

    @Column(nullable = false, length = 100)
    private String luckyName;

    @Column(length = 100)
    private String content;

    @Column(length = 50)
    private String luckySize;

    @Column(nullable = false, length = 255)
    private String luckyImage;

    // 응모 시작일
    @Column(nullable = false)
    private LocalDateTime luckyStartDate;

    // 응모 마감일
    @Column(nullable = false)
    private LocalDateTime luckyEndDate;

    // 당첨 발표일
    @Column(nullable = false)
    private LocalDateTime luckyDate;

    // 당첨 인원
    @Column(nullable = false)
    private Integer luckyPeople;

    // 마감 여부
    // false = 진행중, true = 응모 마감
    @Column(nullable = false)
    private boolean endStatus;
}



