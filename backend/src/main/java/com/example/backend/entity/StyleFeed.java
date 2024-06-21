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
public class StyleFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(nullable = false, length = 50)
    private String feedTitle;

    @Column(nullable = false, length = 255)
    private String feedPhoto;

    @Column(nullable = false)
    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public void change(String feedTitle, String feedPhoto) {
        this.feedTitle = feedTitle;
        this.feedPhoto = feedPhoto;
    }
}