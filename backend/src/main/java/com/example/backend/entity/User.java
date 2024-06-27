package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private int grade = 0;

    @Column(length = 50)
    private String nickname;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String profileImg;

    // 디폴트 false = 회원, true = 관리자
    @Column(nullable = false)
    private boolean role;

    @Column(length = 100)
    private String defaultAddr;

    // 디폴트 false = 일반회원, true = 소셜회원
    private boolean social;
}
