package com.example.backend.entity;

import com.example.backend.dto.mypage.addressSettings.AddressReqDto;
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

    // 우편 번호
    @Column(length = 20, nullable = false)
    private String zonecode;

    // 도로명 주소
    @Column(length = 255)
    private String roadAddress;

    // 지번 주소
    @Column(length = 255)
    private String jibunAddress;

    // 상세 주소
    @Column(length = 255, nullable = false)
    private String detailAddress;

    // 추가 주소 정보
    @Column(length = 20, nullable = false)
    private String extraAddress;

    // 배송지명
    @Column(length = 20, nullable = false)
    private String addressName;

    // 기본 배송지 설정 여부
    @Column(nullable = false)
    private Boolean defaultAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;


    public void updateAddress(AddressReqDto addressReqDto) {
        this.zonecode = addressReqDto.getZonecode();
        this.roadAddress = addressReqDto.getRoadAddress();
        this.jibunAddress = addressReqDto.getJibunAddress();
        this.detailAddress = addressReqDto.getDetailAddress();
        this.extraAddress = addressReqDto.getExtraAddress();
        this.addressName = addressReqDto.getAddressName();
        this.defaultAddress = addressReqDto.isDefaultAddress();
    }
}
