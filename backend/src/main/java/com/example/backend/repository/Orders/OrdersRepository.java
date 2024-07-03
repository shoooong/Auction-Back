package com.example.backend.repository.Orders;

import com.example.backend.dto.orders.OrderDetailsDTO;
import com.example.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Long countByUserUserId(Long userId);

    // TODO: QueryDSL로 변경할 것
    @Query("SELECT new com.example.backend.dto.orders.OrderDetailsDTO(p.productImg, p.productName, p.productSize, o.orderPrice, o.orderStatus) " +
            "FROM Orders o JOIN o.product p JOIN o.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY o.orderDate DESC")
    List<OrderDetailsDTO> findOrderDetailsByUserId(@Param("userId") Long userId);
}
