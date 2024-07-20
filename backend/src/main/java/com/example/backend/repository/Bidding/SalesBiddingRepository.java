package com.example.backend.repository.Bidding;

import com.example.backend.dto.mypage.saleHistory.SaleDetailsDto;
import com.example.backend.dto.mypage.saleHistory.SalesStatusCountDto;
import com.example.backend.entity.SalesBidding;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesBiddingRepository extends JpaRepository<SalesBidding, Long> {

    // 전체 구매 입찰 건수
    @Query("SELECT COUNT(s) FROM SalesBidding s WHERE s.user.userId = :userId")
    Long countAllByUserId(Long userId);

    // 상태별 건수
    @Query("SELECT new com.example.backend.dto.mypage.saleHistory.SalesStatusCountDto(s.salesStatus, COUNT(s)) " +
            "FROM SalesBidding s WHERE s.user.userId = :userId " +
            "GROUP BY s.salesStatus")
    List<SalesStatusCountDto> countSalesStatus(Long userId);

    // TODO: QueryDSL로 변경할 것
    // 판매 입찰 상세 정보
    @Query("SELECT new com.example.backend.dto.mypage.saleHistory.SaleDetailsDto(p.productImg, p.productName, p.productSize, s.salesBiddingId, s.salesBiddingPrice, s.salesStatus) " +
            "FROM SalesBidding s JOIN s.product p JOIN s.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY s.salesBiddingTime DESC")
    List<SaleDetailsDto> findSaleDetailsByUserId(Long userId);

    // 판매 입찰 상세 정보 - 최근 3건 조회
    @Query("SELECT new com.example.backend.dto.mypage.saleHistory.SaleDetailsDto(p.productImg, p.productName, p.productSize, s.salesBiddingId, s.salesBiddingPrice, s.salesStatus) " +
            "FROM SalesBidding s JOIN s.product p JOIN s.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY s.salesBiddingTime DESC")
    List<SaleDetailsDto> findRecentSaleDetailsByUserId(Long userId, Pageable pageable);

    Optional<SalesBidding> findBySalesBiddingIdAndUserUserId(Long salesBiddingId, Long userId);

    @Query("SELECT s FROM SalesBidding s LEFT JOIN Product p on s.product.productId = p.productId WHERE p.modelNum = :modelNum ORDER BY s.salesBiddingTime asc")
    List<SalesBidding> findFirstByOriginalContractDate(@Param("modelNum") String modelNum);
}
