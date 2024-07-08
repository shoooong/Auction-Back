package com.example.backend.repository.CouponIssue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long couponIssuedCount(String couponPolicyId) {
        String issuedCountKey = "coupon:time-attack:" + couponPolicyId + ":issued:count";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment(issuedCountKey);
    }

    public void issuedCancel(String couponPolicyId) {
        String issuedCountKey = "coupon:time-attack:" + couponPolicyId + ":issued:count";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.decrement(issuedCountKey);
    }

    public boolean add(String couponPolicyId, String userId) {
        String userSetKey = "coupon:time-attack:" + couponPolicyId + ":users";
        return redisTemplate.opsForSet().add(userSetKey, userId) != null;
    }
}