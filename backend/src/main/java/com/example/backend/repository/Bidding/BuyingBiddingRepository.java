package com.example.backend.repository.Bidding;

import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.BiddingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BuyingBiddingRepository extends JpaRepository<BuyingBidding, Long> {
    List<BuyingBidding> findByProduct(Product product);

    // 진행 중 건수
    @Query("SELECT COUNT(b) FROM BuyingBidding b WHERE b.user.userId = :userId AND b.biddingStatus ='PROCESS'")
    Long countProcessByUserId(Long userId);

    // 구매 입찰 - 즉시 구매가
    @Query("SELECT MIN(b.buyingBiddingPrice) FROM BuyingBidding b WHERE b.product.productId IN :productIdList AND b.biddingStatus = 'PROCESS'")
    Long findLowPrice(List<Long> productIdList);

    Optional<BuyingBidding> findByBuyingBiddingIdAndUserUserId(Long buyingBiddingId, Long userId);

    List<BuyingBidding> findByProductAndBiddingStatus(Product product, BiddingStatus biddingStatus);
}
