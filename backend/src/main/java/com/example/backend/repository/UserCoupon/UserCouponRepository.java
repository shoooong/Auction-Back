package com.example.backend.repository.UserCoupon;

import com.example.backend.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    @Query("SELECT COUNT(uc) FROM UserCoupon uc WHERE uc.user.userId = :userId AND uc.endDate >= CURRENT_DATE AND uc.useStatus = false")
    Long countValidCouponsByUserId(Long userId);
}
