package com.example.backend.dto.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserFindEmailReqDto {

    private String nickname;
    private String phoneNum;
}
