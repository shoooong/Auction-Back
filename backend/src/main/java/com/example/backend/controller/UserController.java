package com.example.backend.controller;

import com.example.backend.dto.user.UserDTO;
import com.example.backend.dto.user.UserRegisterDTO;
import com.example.backend.security.JWTUtil;
import com.example.backend.service.user.RefreshTokenService;
import com.example.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;

    // 일반회원 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestPart(required = false) MultipartFile file,
                                          @RequestPart UserRegisterDTO userRegisterDTO) {

        userService.registerUser(userRegisterDTO, file, false);
        return ResponseEntity.ok("User registered successfully");
    }

    // 관리자 회원가입
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestPart(required = false) MultipartFile file,
                                           @RequestPart UserRegisterDTO userRegisterDTO) {

        userService.registerUser(userRegisterDTO, file,  true);
        return ResponseEntity.ok("Admin registered successfully");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok().build();
    }

    // 카카오 소셜 로그인
    @GetMapping("/kakao")
    public Map<String, Object> getUserFromKakao(@RequestHeader("Authorization") String authHeader) {
        String accessToken = authHeader.substring(7);

        UserDTO userDTO = userService.getKakaoMember(accessToken);

        Map<String, Object> claims = userDTO.getClaims();

        String kakaoAccessToken = jwtUtil.generateToken(claims, 30);         // 30분
        String kakaoRefreshToken = jwtUtil.generateToken(claims, 60*24);     // 1일

        claims.put("accessToken", kakaoAccessToken);
        claims.put("refreshToken", kakaoRefreshToken);

        return claims;
    }

    // 토큰 재발행
    @PostMapping("/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, String> token) {
        String refreshToken = token.get("refreshToken");

        return refreshTokenService.refreshToken(authHeader, refreshToken);
    }

}
