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

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "luckyId", nullable = false)
    private LuckyDraw luckyDraw;
}
