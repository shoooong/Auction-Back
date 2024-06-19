package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class LuckyDraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long luckyId;

    @Column(nullable = false, length = 50)
    private String luckyName;

    @Column(length = 50)
    private String luckySize;

    @Column(nullable = false, length = 255)
    private String luckyImage;

    @Column(nullable = false)
    private LocalDate luckyStartDate;

    @Column(nullable = false)
    private LocalDate luckyEndDate;

    @Column(nullable = false)
    private LocalDate luckyDate;

    @Column(nullable = false)
    private Integer luckyPeople;






}
