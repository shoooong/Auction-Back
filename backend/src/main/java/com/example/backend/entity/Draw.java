package com.example.backend.entity;


import com.example.backend.entity.enumData.LuckyStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drawId;

    @Enumerated(EnumType.STRING)
    private LuckyStatus luckyStatus;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "luckyId")
    private LuckyDraw luckyDraw;
}
