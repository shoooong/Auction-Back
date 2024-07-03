package com.example.backend.repository.Bidding;

import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyingBiddingRepository extends JpaRepository<BuyingBidding, Long> {
    List<BuyingBidding> findByProduct(Product product);
}
