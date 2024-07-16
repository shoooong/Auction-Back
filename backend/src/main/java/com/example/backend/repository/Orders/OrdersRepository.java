package com.example.backend.repository.Orders;

import com.example.backend.dto.mypage.buyHistory.OrderDetailsDto;
import com.example.backend.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Long countByUserUserId(Long userId);

    // TODO: QueryDSL로 변경할 것
    // 주문 내역 상세 정보 (구매 입찰 내역 포함)
    @Query("SELECT new com.example.backend.dto.mypage.buyHistory.OrderDetailsDto(p.productImg, p.productName, p.productSize, o.orderPrice, o.orderStatus) " +
            "FROM Orders o JOIN o.product p JOIN o.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY o.createDate DESC")
    List<OrderDetailsDto> findOrderDetailsByUserId(@Param("userId") Long userId);

    // 주문 내역 상세 정보 - 최근 3건 조회
    @Query("SELECT new com.example.backend.dto.mypage.buyHistory.OrderDetailsDto(p.productImg, p.productName, p.productSize, o.orderPrice, o.orderStatus) " +
            "FROM Orders o JOIN o.product p JOIN o.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY o.createDate DESC")
    List<OrderDetailsDto> findRecentOrderDetailsByUserId(@Param("userId") Long userId, Pageable pageable);


}
