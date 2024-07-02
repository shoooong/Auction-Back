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

    private LocalDate alarmDate;

    @Column(nullable = false, length = 150)
    private String alarmTitle;

    @Column(nullable = false, length = 255)
    private String alarmContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmUserId", nullable = false)
    private Users users;

    @Column(nullable = false)
    private AlarmType alarmType;

    @Column(nullable = false)
    private Boolean alarmRead;

}
