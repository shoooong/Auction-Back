package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;

@Getter
@Setter
@ToString
public class UserDTO extends User {

    private String email;
    private String password;
    private int grade;
    private String nickname;
    private String phone;
    private String defaultAddr;
    private boolean social;
    private boolean role;

    public UserDTO(String email, String password, int grade, String nickname, String phone, String defaultAddr, boolean social, boolean role) {

        super(email, password, List.of(role ? new SimpleGrantedAuthority("ROLE_ADMIN") : new SimpleGrantedAuthority("ROLE_USER")));this.email = email;
        this.password = password;
        this.grade = grade;
        this.nickname = nickname;
        this.phone = phone;
        this.defaultAddr = defaultAddr;
        this.social = social;
        this.role = role;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("password", password);
        claims.put("nickname", nickname);
        claims.put("grade", grade);
        claims.put("phone", phone);
        claims.put("defaultAddr", defaultAddr);
        claims.put("social", social);
        claims.put("role", role);

        return claims;
    }
}
