package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
public class LuckyDrawAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long luckyAnnouncementId;

    @Column(nullable = false, length = 100)
    private String luckyTitle;

    @Column(nullable = false, length = 255)
    private String luckyContent;

    @OneToOne
    @JoinColumn(name = "luckyId")
    private LuckyDraw luckyDraw;
}
