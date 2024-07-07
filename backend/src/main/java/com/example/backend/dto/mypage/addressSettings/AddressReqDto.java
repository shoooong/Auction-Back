package com.example.backend.dto.mypage.addressSettings;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressReqDto {

    private String zoneCode;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String extraAddress;
    private String addressName;
    private boolean defaultAddress;

}
