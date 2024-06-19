package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Announcement {

    @Id
    @Column(nullable = false)
    private Long announcementId;

    @Column(nullable = false, length = 50)
    private String announceTitle;

    @Column(nullable = false)
    private LocalDateTime registerDate;

    @Column(nullable = false)
    private String announceContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

}
