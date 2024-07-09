package com.example.backend.security.handler;

import com.example.backend.Jwt.util.JWTUtil;
import com.example.backend.dto.user.UserDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-------------onAuthenticationSuccess--------------");

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        Map<String, Object> claims = userDTO.getClaims();

        String accessToken = JWTUtil.generateToken(claims, 30);         // 30분
        String refreshToken = JWTUtil.generateToken(claims, 60*24);     // 1일

        log.info("AccessToken : " + accessToken);
        log.info("RefreshToken : " + refreshToken);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
