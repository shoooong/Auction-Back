package com.example.backend.repository.Orders;

import com.example.backend.dto.mypage.buyHistory.BuyDetailsDto;
import com.example.backend.dto.mypage.buyHistory.BuyDetailsProcessDto;
import com.example.backend.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Long countByUserUserId(Long userId);

    // 종료 건수
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.user.userId = :userId AND o.orderStatus ='COMPLETE'")
    Long countCompleteByUserId(Long userId);

    // TODO: QueryDSL로 변경할 것
    // 주문 내역 상세 정보 - 전체
    @Query("SELECT new com.example.backend.dto.mypage.buyHistory.BuyDetailsDto(p.productImg, p.productName, p.productSize, o.orderPrice, o.orderStatus) " +
            "FROM Orders o JOIN o.product p JOIN o.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY o.modifyDate DESC")
    List<BuyDetailsDto> findAllOBuyDetails(Long userId);
    // 프론트에서 orderStatus = WAITING 이면, "입찰 중"
    //         orderStatus = COMPLETE 면, "종료"
    //         orderStatus = CANCEL 이면, "취소"

    // 주문 내역 상세 정보 - 입찰 중
    @Query("SELECT new com.example.backend.dto.mypage.buyHistory.BuyDetailsProcessDto(p.productImg, p.productName, p.productSize, bb.buyingBiddingPrice, bb.biddingStatus) " +
            "FROM BuyingBidding bb JOIN bb.product p JOIN bb.user u " +
            "WHERE u.userId = :userId " +
            "AND bb.biddingStatus = 'PROCESS' " +
            "ORDER BY bb.createDate DESC")
    List<BuyDetailsProcessDto> findBuyDetailsProcess(Long userId);

    // 주문 내역 상세 정보 - 종료
    @Query("SELECT new com.example.backend.dto.mypage.buyHistory.BuyDetailsDto(p.productImg, p.productName, p.productSize, o.orderPrice, o.orderStatus) " +
            "FROM Orders o JOIN o.product p JOIN o.user u " +
            "WHERE u.userId = :userId " +
            "AND o.orderStatus = 'COMPLETE' " +
            "ORDER BY o.modifyDate DESC")
    List<BuyDetailsDto> findBuyDetailsComplete(Long userId);

    // 주문 내역 상세 정보 - 전체 (최근 3건 조회)
    @Query("SELECT new com.example.backend.dto.mypage.buyHistory.BuyDetailsDto(p.productImg, p.productName, p.productSize, o.orderPrice, o.orderStatus) " +
            "FROM Orders o JOIN o.product p JOIN o.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY o.modifyDate DESC")
    List<BuyDetailsDto> findRecentOrderDetails(Long userId, Pageable pageable);


}
