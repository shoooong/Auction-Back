package com.example.backend.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDTO {

    private String email;
    private String password;
    private String nickname;
    private String phone;
}
