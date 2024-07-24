package com.example.backend.repository.User;

import com.example.backend.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private JWTUtil jwtUtil;

    @Autowired
    public RefreshTokenRepository(RedisTemplate<String, String> redisTemplate, JWTUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }


    public void saveRefreshToken(String email, String refreshToken, Long expiration) {
        redisTemplate.opsForValue().set(
                email, refreshToken, jwtUtil.extractExpiration(refreshToken), TimeUnit.SECONDS
        );
    }

    /**
     * 사용자 이메일(key)로 refreshToken(value) 조회
     */
    public String getRefreshToken(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    /**
     * newRefreshToken 저장
     */
    public void updateRefreshToken(String email, String newRefreshToken, Long expiration) {
        redisTemplate.delete(email);

        redisTemplate.opsForValue().set(
                email, newRefreshToken, expiration, TimeUnit.SECONDS
        );
    }
}
