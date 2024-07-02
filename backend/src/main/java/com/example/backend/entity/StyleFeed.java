package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class StyleFeed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(nullable = false, length = 150)
    private String feedTitle;

    @Column(nullable = false, length = 255)
    private String feedImage;

    @Column(nullable = false)
    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    public void change(String feedTitle, String feedImage) {
        this.feedTitle = feedTitle;
        this.feedImage = feedImage;
    }
}