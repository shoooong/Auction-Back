package com.example.backend.dto.mypage.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDto {

    private String profileImg;
    private String nickname;
    private String email;

    private int grade;
}
