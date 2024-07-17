package com.example.backend.security.handler;

import com.example.backend.security.JWTUtil;
import com.example.backend.dto.user.UserDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-------------onAuthenticationSuccess--------------");

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        Map<String, Object> claims = userDTO.getClaims();

        String accessToken = jwtUtil.generateToken(claims, 300);          // 30분
        String refreshToken = jwtUtil.generateToken(claims, 60*24);

        redisTemplate.opsForValue().set(
                userDTO.getUsername(),
                refreshToken,
                jwtUtil.getExpiration(refreshToken),
                TimeUnit.SECONDS
        );
        log.info("redis set key: {}, value: {}", userDTO.getUsername(), refreshToken);

        log.info("AccessToken : {}", accessToken);
        log.info("RefreshToken : {}", refreshToken);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);



        // HttpOnly 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS 환경에서만 작동하도록 설정
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 30); // 30분 (초 단위)

        // HttpOnly 쿠키 설정 (RefreshToken)
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 환경에서만 작동하도록 설정
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24); // 1일 (초 단위)

        // 쿠키를 응답에 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);


        Gson gson = new Gson();
//        claims.remove("accessToken");
//        claims.remove("refreshToken");
        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
