package com.example.backend.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserModifyResDto {

    private String email;
    private String nickname;
    private String phoneNum;
    private String profileImg;
}
