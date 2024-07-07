package com.example.backend.dto.mypage.addressSettings;

import com.example.backend.entity.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private Long addressId;
    private String zoneCode;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String extraAddress;
    private String addressName;
    private boolean defaultAddress;


    public static AddressDto fromEntity(Address address){
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .zoneCode(address.getZoneCode())
                .roadAddress(address.getRoadAddress())
                .jibunAddress(address.getJibunAddress())
                .detailAddress(address.getDetailAddress())
                .extraAddress(address.getExtraAddress())
                .addressName(address.getAddressName())
                .defaultAddress(address.getDefaultAddress())
                .build();
    }
}
