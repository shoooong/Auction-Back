package com.example.backend.producer;

import com.example.backend.dto.coupon.CouponCreateDto;
import com.example.backend.dto.coupon.CouponIssueDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponCreateProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CouponCreateProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(Long couponPolicyId, Long userId) {
        String key = "coupon:time-attack:" + couponPolicyId + ":user:" + userId;
        kafkaTemplate.send("CouponIssueTopic", key);
    }
}


