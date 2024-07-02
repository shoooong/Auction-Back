package com.example.backend.dto.user;

import com.example.backend.entity.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Getter
@Setter
@ToString
public class UserDTO extends User {

    private Long userId;
    private String email;
    private String password;
    private int grade;
    private String nickname;
    private String phoneNum;
    private boolean social;
    private boolean role;

    public UserDTO(Long userId, String email, String password, int grade, String nickname, String phoneNum, boolean social, boolean role) {

        super(email, password, List.of(role ? new SimpleGrantedAuthority("ROLE_ADMIN") : new SimpleGrantedAuthority("ROLE_USER")));

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.grade = grade;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.social = social;
        this.role = role;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("password", password);
        claims.put("nickname", nickname);
        claims.put("grade", grade);
        claims.put("phone", phoneNum);
        claims.put("social", social);
        claims.put("role", role);

        return claims;
    }

    public Users toEntity() {
        return Users.builder()
                .userId(this.userId)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .phoneNum(this.phoneNum)
                .social(this.social)
                .role(this.role)
                .build();
    }
}
