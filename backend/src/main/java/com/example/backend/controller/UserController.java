package com.example.backend.controller;

import com.example.backend.exception.CustomJWTException;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.dto.user.UserRegisterDTO;
import com.example.backend.security.JWTUtil;
import com.example.backend.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JWTUtil jwtUtil;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {

        // TODO: email, password 확인을 위해 작성, 추후에 삭제할 것
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        log.info("Login request: email={}, password={}", email, password);

        return ResponseEntity.ok().build();
    }

    // 카카오 소셜 로그인
    @GetMapping("/kakao")
    public Map<String, Object> getUserFromKakao(@RequestHeader("Authorization") String authHeader) {
        log.info("authHeader={}", authHeader);

        String accessToken = authHeader.substring(7);

        log.info("accessToken={}", accessToken);

        UserDTO userDTO = userService.getKakaoMember(accessToken);

        Map<String, Object> claims = userDTO.getClaims();

        String kakaoAccessToken = jwtUtil.generateToken(claims, 10);         // 10분
        String kakaoRefreshToken = jwtUtil.generateToken(claims, 60*24);     // 1일

        log.info("kakaoAccessToken={}", kakaoAccessToken);
        log.info("kakaoRefreshToken={}", kakaoRefreshToken);

        claims.put("accessToken", kakaoAccessToken);
        claims.put("refreshToken", kakaoRefreshToken);

        return claims;
    }


    // TODO: 중복 가입 예외처리 (닉네임, 비밀번호)
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


    // refreshToken 재발행
    // TODO: 시간별 재발행 여부 test
    @PostMapping("/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
                                       @CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        // 1. accessToken이 없거나 잘못된 JWT인 경우, 예외 메시지 발생
        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH_TOKEN");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID_REFRESH_TOKEN") ;
        }

        String accessToken = authHeader.substring(7);
        log.info("Received access token: " + accessToken);

        // 2. accessToken의 유효기간 남은 경우, 전달된 토큰들을 그대로 전송
        if (!jwtUtil.checkExpiredToken(accessToken)) {
            log.info("Access token is still valid.");
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // refreshToken 검증
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

        log.info("refresh ... claims: " + claims );

        // 3. accessToken이 만료, refreshToken은 만료되지 않은 경우, 새로운 accessToken만 생성하여 전송
        String newAccessToken = jwtUtil.generateToken(claims, 30);

        String newRefreshToken = jwtUtil.checkTime((Integer) claims.get("exp")) ?
                jwtUtil.generateToken(claims, 60 * 24) : refreshToken;


        // HttpOnly 쿠키 설정 (새로운 AccessToken)
//        Cookie newAccessTokenCookie = new Cookie("accessToken", newAccessToken);
//        newAccessTokenCookie.setHttpOnly(true);
//        newAccessTokenCookie.setSecure(true);
//        newAccessTokenCookie.setPath("/");
//        newAccessTokenCookie.setMaxAge(30 * 60); // 30분
//        response.addCookie(newAccessTokenCookie);

        // HttpOnly 쿠키 설정 (새로운 RefreshToken이 생성된 경우에만 설정)
//        if (!newRefreshToken.equals(refreshToken)) {
//            Cookie newRefreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
//            newRefreshTokenCookie.setHttpOnly(true);
//            newRefreshTokenCookie.setSecure(true);
//            newRefreshTokenCookie.setPath("/");
//            newRefreshTokenCookie.setMaxAge(60 * 60 * 24); // 1일
//            response.addCookie(newRefreshTokenCookie);
//        }


        return Map.of("newAccessToken", newAccessToken, "newRefreshToken", newRefreshToken);
    }

}
