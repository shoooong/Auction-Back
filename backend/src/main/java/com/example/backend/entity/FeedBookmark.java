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
public class FeedBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long styleSavedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedId")
    private StyleFeed styleFeed;
}