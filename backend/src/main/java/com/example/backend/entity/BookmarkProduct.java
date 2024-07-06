package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class BookmarkProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
}
