package com.example.backend.security;

import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("========loadUserByUsername 메서드 호출========");

        User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 email 입니다."));

        log.info("User found: {}", user);

        UserDTO userDTO = new UserDTO(
                user.getEmail(),
                user.getPassword(),
                user.getGrade(),
                user.getNickname(),
                user.getPhoneNum(),
                user.isSocial(),
                user.isRole()
        );

        log.info(userDTO);

        return userDTO;
    }
}
