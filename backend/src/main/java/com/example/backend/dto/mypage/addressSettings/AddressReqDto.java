package com.example.backend.dto.mypage.addressSettings;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressReqDto {

    private String name;
    @NotBlank
    private String zonecode;

    private String roadAddress;

    private String jibunAddress;

    private String detailAddress;

    private String extraAddress;

    @NotBlank
    private String addressName;

    private boolean defaultAddress;

}
