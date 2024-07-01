package com.example.backend.entity;

import com.example.backend.dto.user.AddressDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(length = 255, nullable = false)
    private String zoneNo;

    @Column(length = 255, nullable = false)
    private String addressName;

    @Column(nullable = false)
    private Boolean defaultAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    public void updateAddress(AddressDTO addressDTO) {
        this.zoneNo = addressDTO.getZoneNo();
        this.addressName = addressDTO.getAddressName();
        this.defaultAddress = addressDTO.isDefaultAddress();
    }
}
