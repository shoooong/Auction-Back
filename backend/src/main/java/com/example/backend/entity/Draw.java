package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drawId;

    private boolean luckyStatus;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "luckyId")
    private LuckyDraw luckyDraw;
}
