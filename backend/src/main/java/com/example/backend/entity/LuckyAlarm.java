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
public class LuckyAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long luckyAlarmId;

    @Column(nullable = false)
    private String luckyTitle;

    @Column(nullable = false)
    private LocalDate luckyRegDate;

    @Column(nullable = false)
    private String luckyContent;

    @OneToOne
    @JoinColumn(name = "luckyId")
    private LuckyDraw luckyDraw;
}