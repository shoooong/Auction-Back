package com.example.backend.dto.mypage.addressSettings;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressReqDto {

    @NotBlank
    private String zoneCode;

    @NotBlank
    private String roadAddress;

    @NotBlank
    private String jibunAddress;

    @NotBlank
    private String detailAddress;

    @NotBlank
    private String extraAddress;

    @NotBlank
    private String addressName;

    private boolean defaultAddress;

}
