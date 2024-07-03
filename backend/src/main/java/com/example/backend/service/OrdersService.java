package com.example.backend.service;

import com.example.backend.dto.orders.BuyHistoryDTO;
import com.example.backend.dto.orders.OrderDetailsDTO;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final BuyingBiddingRepository buyingBiddingRepository;

    /**
     * 구매 내역
     * 전체, 진행 중, 종료 건수
     * 구매 내역 (상품사진, 상품명, 상품사이즈, 결제금액, 주문상태)
     * 주문날짜 기준 최신순 정렬
     */
    public BuyHistoryDTO getBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = buyingBiddingRepository.countCompleteByUserId(userId);

        List<OrderDetailsDTO> orderDetailsDTO = ordersRepository.findOrderDetailsByUserId(userId);

        return BuyHistoryDTO.builder()
                .allCount(allCount)
                .processCount(processCount)
                .completeCount(completeCount)
                .orderDetails(orderDetailsDTO)
                .build();
    }
}
