package com.example.backend.security;

import com.example.backend.dto.UserDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("========loadUserByUsername 메서드 호출========");

        Optional<User> optUser = userRepository.findByEmail(username);

        if(optUser.isEmpty()) {
            throw new UsernameNotFoundException("Not Found");
        }

        User user = optUser.get();

        log.info("User found: {}", user);

        UserDTO userDTO = new UserDTO(
                user.getEmail(),
                user.getPassword(),
                user.getGrade(),
                user.getNickname(),
                user.getPhone(),
                user.getDefaultAddr(),
                user.isSocial(),
                user.isRole()
        );

        log.info(userDTO);

        return userDTO;
    }
}
