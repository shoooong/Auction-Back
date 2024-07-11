package com.example.backend.repository.CouponIssue;

import com.example.backend.entity.enumData.CouponCondition;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;


    @Autowired
    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private static final String COUPON_TYPE_KEY= "coupon:time-attack:condition";


    public Long couponIssuedCount(Long couponId) {
        String issuedCountKey = COUPON_TYPE_KEY + couponId + ":issued:count";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment(issuedCountKey);
    }

    public void issuedCancel(Long couponId) {
        String issuedCountKey = COUPON_TYPE_KEY + couponId + ":issued:count";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.decrement(issuedCountKey);
    }

    public Long registerCouponUser(Long couponId, Long userId) {
        String userSetKey = COUPON_TYPE_KEY + couponId + ":users";
        return redisTemplate.opsForSet().add(userSetKey, String.valueOf(userId));
    }


    // 쿠폰의 발급 조건 저장, conditionKey(발급조건이름), conditionValue(발급조건)
    public void saveCouponCondition(String couponId, CouponCondition conditionKey, String conditionValue) {
        hashOperations.put(COUPON_TYPE_KEY + ":" + couponId, conditionKey.name(), conditionValue);
    }

    // 해당 쿠폰의 특정 발급 조건 검색
    public String getCouponCondition(Long couponId, String conditionKey) {
        return hashOperations.get(COUPON_TYPE_KEY + ":" + couponId, conditionKey);
    }


    // 해당 쿠폰의 모든 발급 조건 조회
    public Map<String, String> getAllCouponConditions(Long couponId) {
        return hashOperations.entries(COUPON_TYPE_KEY + ":" + couponId);
    }


    // 해당 쿠폰의 모든 발급 조건 삭제
    public void deleteCouponConditions(Long couponId) {
        redisTemplate.delete(COUPON_TYPE_KEY + ":" + couponId);
    }

    /* Coupon 생성시 발급 조건 저장
     * 발급 가능한 날짜, 총 발급 수량
     * */


}