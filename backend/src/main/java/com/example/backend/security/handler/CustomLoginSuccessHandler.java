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

        String accessToken = jwtUtil.generateToken(claims, 1);
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



        // 쿠키 설정
//        String domain = "www.sho0ong.com";
        String domain = request.getServerName();

//        String accessTokenCookie = String.format("accessToken=%s; Max-Age=%d; Expires=%s; Path=/; HttpOnly; Secure=true; Domain=%s; SameSite=Lax",
//                accessToken, 120, "Wed, 21 Oct 2024 07:28:00 GMT", domain);
//        String refreshTokenCookie = String.format("refreshToken=%s; Max-Age=%d; Path=/; HttpOnly; Secure=false; Domain=%s; SameSite=Lax",
//                refreshToken, 60 * 60 * 24, domain);
//        String isLoginCookie = String.format("isLogin=%s; Max-Age=%d; Path=/; Secure=false; Domain=%s; SameSite=Lax",
//                accessToken, 120, domain);



//        response.addHeader("Set-Cookie", accessTokenCookie);
//        response.addHeader("Set-Cookie", refreshTokenCookie);
//        response.addHeader("Set-Cookie", isLoginCookie);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 10);
        accessTokenCookie.setDomain(domain);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24);
        refreshTokenCookie.setDomain(domain);

        Cookie isLoginCookie = new Cookie("isLogin", "true");
        isLoginCookie.setHttpOnly(false);
        isLoginCookie.setSecure(false);
        isLoginCookie.setPath("/");
        isLoginCookie.setMaxAge(60 * 10);
        isLoginCookie.setDomain(domain);

        // 쿠키를 응답에 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        response.addCookie(isLoginCookie);


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
