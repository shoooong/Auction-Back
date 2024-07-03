package com.example.backend.dto.mypage.addressSettings;

import com.example.backend.entity.Address;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class AddressDTO {

    private Long addressId;
    private String zoneNo;
    private String addressName;
    private boolean defaultAddress;


    public static AddressDTO fromEntity(Address address){
        return AddressDTO.builder()
                .addressId(address.getAddressId())
                .zoneNo(address.getZoneNo())
                .addressName(address.getAddressName())
                .defaultAddress(address.getDefaultAddress())
                .build();
    }

    public static Address toEntity(AddressDTO addressDTO){
        return Address.builder()
                .addressId(addressDTO.getAddressId())
                .zoneNo(addressDTO.getZoneNo())
                .addressName(addressDTO.getAddressName())
                .build();
    }
}
