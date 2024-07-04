package com.example.backend.repository.Bidding;

import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyingBiddingRepository extends JpaRepository<BuyingBidding, Long> {
    List<BuyingBidding> findByProduct(Product product);

    // 전체 구매 입찰 건수
//    @Query("SELECT COUNT(b) FROM BuyingBidding b WHERE b.user.userId = :userId")
//    Long countAllByUserId(Long userId);

    // 진행 중 건수
    @Query("SELECT COUNT(b) FROM BuyingBidding b WHERE b.user.userId = :userId AND b.biddingStatus ='PROCESS'")
    Long countProcessByUserId(Long userId);

    // 종료 건수
    @Query("SELECT COUNT(b) FROM BuyingBidding b WHERE b.user.userId = :userId AND b.biddingStatus ='COMPLETE'")
    Long countCompleteByUserId(Long userId);

    // 구매 입찰 - 즉시 구매가
    @Query("SELECT b.buyingBiddingPrice FROM BuyingBidding b WHERE b.product.productId = :productId AND b.biddingStatus = 'PROCESS'")
    Long nowLowPrice(Long productId);

}
