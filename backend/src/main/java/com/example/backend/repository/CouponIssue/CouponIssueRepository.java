package com.example.backend.repository.CouponIssue;

import com.example.backend.entity.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponIssueRepository extends JpaRepository<CouponIssue,Long> {

    @Query("SELECT COUNT(ci) FROM CouponIssue ci WHERE ci.user.userId = :userId AND ci.endDate >= CURRENT_DATE AND ci.useStatus = false")
    Long countValidCouponsByUserId(Long userId);
}
