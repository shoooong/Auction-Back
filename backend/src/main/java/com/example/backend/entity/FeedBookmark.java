package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
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

    public void setUser(User newUser) {
    }

    public void setStyleFeed(StyleFeed newStyleFeed) {

    }
}