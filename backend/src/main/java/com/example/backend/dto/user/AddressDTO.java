package com.example.backend.dto.user;

import com.example.backend.entity.Address;
import com.example.backend.entity.User;
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
    private User user;


    public static AddressDTO fromEntity(Address address){
        return AddressDTO.builder()
                .addressId(address.getAddressId())
                .zoneNo(address.getZoneNo())
                .addressName(address.getAddressName())
                .user(address.getUser())
                .build();
    }

    public static Address toEntity(AddressDTO addressDTO){
        return Address.builder()
                .addressId(addressDTO.getAddressId())
                .zoneNo(addressDTO.getZoneNo())
                .addressName(addressDTO.getAddressName())
                .user(addressDTO.getUser())
                .build();
    }
}
