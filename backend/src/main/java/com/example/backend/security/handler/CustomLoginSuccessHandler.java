package com.example.backend.security.handler;

import com.example.backend.security.JWTUtil;
import com.example.backend.dto.user.UserDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
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

        String accessToken = jwtUtil.generateToken(claims, 30);
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
