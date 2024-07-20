package com.example.backend.service;

import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Orders;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.OrderStatus;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyingBiddingService {

    private final BuyingBiddingRepository buyingBiddingRepository;
    private final OrdersRepository ordersRepository;

    @Transactional
    public void cancelBuyingBidding(Long userId, Long buyingBiddingId) {
        BuyingBidding buyingBidding = buyingBiddingRepository.findByBuyingBiddingIdAndUserUserId(buyingBiddingId, userId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매 입찰 내역입니다."));

        buyingBidding.changeBiddingStatus(BiddingStatus.CANCEL);
        buyingBiddingRepository.save(buyingBidding);

        Optional<Orders> optOrder = ordersRepository.findByBuyingBiddingBuyingBiddingId(buyingBiddingId);
        optOrder.ifPresent(order -> {
            order.changeOrderStatus(OrderStatus.CANCEL);
            ordersRepository.save(order);
        });
    }
}
