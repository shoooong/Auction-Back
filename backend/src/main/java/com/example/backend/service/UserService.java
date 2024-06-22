package com.example.backend.service;

import com.example.backend.dto.user.UserRegisterDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRegisterDTO userRegisterDTO, boolean isAdmin) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // TODO: 이후 주소 api 호출 기능 구현되면, 회원가입 시 기본 배송지 입력 받도록 추가
        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .nickname(userRegisterDTO.getNickname())
                .phone(userRegisterDTO.getPhone())
                .role(isAdmin)
                .build();

        userRepository.save(user);
    }
}
