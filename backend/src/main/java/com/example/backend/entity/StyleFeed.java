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
    @Column(nullable = false)
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

}
