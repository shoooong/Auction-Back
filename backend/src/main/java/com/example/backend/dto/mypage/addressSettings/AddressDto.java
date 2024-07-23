package com.example.backend.dto.mypage.addressSettings;

import com.example.backend.entity.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private Long addressId;
    private String name;
    private String addrPhone;
    private String zonecode;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String extraAddress;
    private boolean defaultAddress;


    public static AddressDto fromEntity(Address address){
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .name(address.getName())
                .addrPhone(address.getAddrPhone())
                .zonecode(address.getZonecode())
                .roadAddress(address.getRoadAddress())
                .jibunAddress(address.getJibunAddress())
                .detailAddress(address.getDetailAddress())
                .extraAddress(address.getExtraAddress())
                .defaultAddress(address.getDefaultAddress())
                .build();
    }
}
