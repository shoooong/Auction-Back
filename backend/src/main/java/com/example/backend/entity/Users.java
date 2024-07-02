package com.example.backend.entity;

import com.example.backend.dto.user.UserModifyDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Users extends BaseEntity {
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
    private String phoneNum;

    @Column(length = 255)
    private String profileImg;

    // 디폴트 false = 회원, true = 관리자
    @Column(nullable = false)
    private boolean role;

    // 디폴트 false = 일반회원, true = 소셜회원
    @Column(nullable = false)
    private boolean social;


    public void updateUser(UserModifyDTO userModifyDTO, PasswordEncoder passwordEncoder) {
        this.email = userModifyDTO.getEmail();
        this.password = passwordEncoder.encode(userModifyDTO.getPassword());
        this.nickname = userModifyDTO.getNickname();
        this.phoneNum = userModifyDTO.getPhoneNum();
        this.profileImg = userModifyDTO.getProfileImg();
    }
}
