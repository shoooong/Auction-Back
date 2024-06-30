package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
