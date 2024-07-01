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

//    @Column(nullable = false)
//    private LocalDateTime luckyStartDate;
//
//    @Column(nullable = false)
//    private LocalDateTime luckyEndDate;
//
//    @Column(nullable = false)
//    private LocalDateTime luckyDate;

    @Column(nullable = false)
    private Integer luckyPeople;
}



