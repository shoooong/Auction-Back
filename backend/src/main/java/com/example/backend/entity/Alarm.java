package com.example.backend.entity;

import com.example.backend.entity.enumData.AlarmType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AlarmType alarmType;

    private LocalDateTime alarmDate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean alarmRead = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmUserId", nullable = false)
    private Users users;

}
