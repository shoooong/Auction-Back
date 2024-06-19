package com.example.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    private Long userId;

    private String email;
    private String password;
    private Long grade;
    private String nickname;
    private String phone;
    private String profileImg;
    private String Role;

    private LocalDate createDate;
}
