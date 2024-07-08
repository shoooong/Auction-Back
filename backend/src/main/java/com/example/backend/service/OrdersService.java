package com.example.backend.service;

import com.example.backend.dto.mypage.buyHistory.BuyHistoryDto;
import com.example.backend.dto.mypage.buyHistory.OrderDetailsDto;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public BuyHistoryDto getBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = buyingBiddingRepository.countCompleteByUserId(userId);

        List<OrderDetailsDto> orderDetailsDto = ordersRepository.findOrderDetailsByUserId(userId);

        return BuyHistoryDto.builder()
                .allCount(allCount)
                .processCount(processCount)
                .completeCount(completeCount)
                .orderDetails(orderDetailsDto)
                .build();
    }


    public BuyHistoryDto getRecentBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = buyingBiddingRepository.countCompleteByUserId(userId);

        List<OrderDetailsDto> orderDetailsDto = ordersRepository.findRecentOrderDetailsByUserId(userId, PageRequest.of(0, 3));

        return BuyHistoryDto.builder()
                .allCount(allCount)
                .processCount(processCount)
                .completeCount(completeCount)
                .orderDetails(orderDetailsDto)
                .build();
    }
}
