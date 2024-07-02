package com.example.backend.service;

import com.example.backend.dto.user.AddressDTO;
import com.example.backend.repository.User.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressDTO> getAllAddress(Long userId) {
        return addressRepository.findByUserUserId(userId).stream()
                .map(AddressDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    public void modifyAddress(Long addressId, AddressDTO addressDTO, Long userId) {
//        Address address = addressRepository.findByIdAndUser_UserId(addressId, userId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다."));
//
//        address.updateAddress(addressDTO);
//
//        addressRepository.save(address);
//
//    }
}
