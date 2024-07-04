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
    @Column(nullable = false)
    private AlarmType alarmType;

    private LocalDate alarmDate;

    @Column(nullable = false)
    private Boolean alarmRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmUserId", nullable = false)
    private Users users;
}
