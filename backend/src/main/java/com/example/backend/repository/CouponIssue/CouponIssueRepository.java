package com.example.backend.repository.CouponIssue;

import com.example.backend.entity.Coupon;
import com.example.backend.entity.CouponIssue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponIssueRepository extends JpaRepository<CouponIssue,Long> {

    @Query("SELECT COUNT(ci) FROM CouponIssue ci WHERE ci.user.userId = :userId AND ci.endDate >= CURRENT_DATE AND ci.useStatus = false")
    Long countValidCouponsByUserId(Long userId);

    // 해당 회원의 사용 가능한 발급 쿠폰 내역 조회
    @Query("SELECT ci.coupon FROM CouponIssue ci WHERE ci.user.userId = :userId AND ci.endDate >= CURRENT_DATE AND ci.useStatus = false")
    List<Coupon> findCouponsByUserId(Long userId);
}
