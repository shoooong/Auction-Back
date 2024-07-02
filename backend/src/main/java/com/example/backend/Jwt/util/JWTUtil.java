package com.example.backend.Jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    // JWT 서명에 사용될 비밀키
    // TODO: 네이버클라우드플랫폼 KMS로 변경
    private static final String SECRET_KEY = "qwer1234asdf5678zxcv0000poiu0987lkjh6543mnbv0000!";

    /**
     * JWT 생성 (claim, 유효기간)
     */
    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key;
        String jwtStr = null;

        try {
            key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8"));

            jwtStr = Jwts.builder()
                    .setHeader(Map.of("typ", "JWT"))
                    .setClaims(valueMap)
                    .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                    .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            log.info("Using SecretKey: {}", key);
            log.info("Generated JWT: {}", jwtStr);

        } catch (Exception e) {
            log.error("Error generating SecretKey: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        log.info("Generating JWT with claims: {}", valueMap);

        return jwtStr;
    }

    /**
     * JWT 유효성 검증 및 claims 반환
     */
    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claims;


        try {
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.SECRET_KEY.getBytes("UTF-8"));

            // JWT 검증 및 클레임 추출
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException malformedJwtException) {
            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException invalidClaimException) {
            throw new CustomJWTException("Invalid");
        } catch (JwtException jwtException) {
            throw new CustomJWTException("JWTError");
        } catch (Exception e) {
            throw new CustomJWTException("Error");
        }

        log.info("JWT claims: {}", claims);
        return claims;
    }

    /**
     * 토큰 잔여 유효시간 검사
     */
    public static boolean checkTime(Integer exp) {

        Date expDate = new Date((long) exp * 1000);

        long gap = expDate.getTime() - System.currentTimeMillis();

        long leftMin = gap / (1000 * 60);

        return leftMin < 60;
    }

    /**
     * 토큰 유효성 검사 (만료 여부)
     */
    public static boolean checkExpiredToken(String token) {
        try {
            validateToken(token);
        } catch (CustomJWTException e) {
            if (e.getMessage().equals("Expired")) {
                return true;
            }
        }

        return false;
    }
}
