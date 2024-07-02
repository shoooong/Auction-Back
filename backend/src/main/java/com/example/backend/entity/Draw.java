package com.example.backend.entity;


import com.example.backend.entity.enumData.LuckyStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LuckyStatus luckyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckyId", nullable = false)
    private LuckyDraw luckyDraw;
}
