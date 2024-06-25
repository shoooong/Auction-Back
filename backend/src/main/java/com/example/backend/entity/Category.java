package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    // 카테고리 대분류
    @Column(length = 50, nullable = false)
    private String categoryType;

    // 카테고리 소분류
    @Column(length = 20, nullable = false)
    private String categoryName;
}