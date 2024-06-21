package com.example.backend.controller;

import com.example.backend.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
@Controller
public class UserController {
//    private final CustomUserDetailsService customUserDetailsService;
//    private final AuthenticationManager authenticationManager;
//    private final JWTUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        log.info("Login request: email={}, password={}", email, password);

        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
