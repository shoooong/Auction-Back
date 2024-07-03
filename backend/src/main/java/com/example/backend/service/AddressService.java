package com.example.backend.service;

import com.example.backend.dto.user.AddressDTO;
import com.example.backend.repository.User.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * 배송지 전체 조회
     */
    public List<AddressDTO> getAllAddress(Long userId) {
        return addressRepository.findAllByUserId(userId).stream()
                .map(AddressDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
