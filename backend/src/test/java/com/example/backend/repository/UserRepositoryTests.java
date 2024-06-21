package com.example.backend.repository;

import com.example.backend.entity.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertUser() {
        for (int i = 1; i <= 10; i++) {

            User user = User.builder()
                    .email("ncp" + i + "@naver.com")
                    .password(passwordEncoder.encode(String.valueOf(i).repeat(4)))
                    .nickname("ncp" + i)
                    .role(false)
                    .build();

            userRepository.save(user);
        }
    }
}
