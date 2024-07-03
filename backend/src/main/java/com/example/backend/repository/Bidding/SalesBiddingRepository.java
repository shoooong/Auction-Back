package com.example.backend.repository.Bidding;

import com.example.backend.dto.mypage.saleHistory.SaleDetailsDTO;
import com.example.backend.entity.SalesBidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesBiddingRepository extends JpaRepository<SalesBidding, Long> {

    // 전체 구매 입찰 건수
    @Query("SELECT COUNT(s) FROM SalesBidding s WHERE s.user.userId = :userId")
    Long countAllByUserId(@Param("userId") Long userId);

    // 검수 중 건수
    @Query("SELECT COUNT(s) FROM SalesBidding s WHERE s.user.userId = :userId AND s.salesStatus ='INSPECTION'")
    Long countInspectionByUserId(Long userId);

    // 진행 중 건수
    @Query("SELECT COUNT(s) FROM SalesBidding s WHERE s.user.userId = :userId AND s.salesStatus ='PROCESS'")
    Long countProcessByUserId(Long userId);

    // 종료 건수
    @Query("SELECT COUNT(s) FROM SalesBidding s WHERE s.user.userId = :userId AND s.salesStatus ='COMPLETE'")
    Long countCompleteByUserId(Long userId);

    // TODO: QueryDSL로 변경할 것
    // 판매 입찰 상세 정보
    @Query("SELECT new com.example.backend.dto.mypage.saleHistory.SaleDetailsDTO(p.productImg, p.productName, p.productSize, s.salesPrice, s.salesStatus) " +
            "FROM SalesBidding s JOIN s.product p JOIN s.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY s.salesBiddingTime DESC")
    List<SaleDetailsDTO> findSaleDetailsByUserId(@Param("userId") Long userId);
}
