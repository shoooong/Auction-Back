package com.example.backend.service;

import com.example.backend.dto.mypage.saleHistory.SaleDetailsDto;
import com.example.backend.dto.mypage.saleHistory.SaleHistoryDto;
import com.example.backend.dto.mypage.saleHistory.SalesStatusCountDto;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.enumData.SalesStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesBiddingService {

    private final SalesBiddingRepository salesBiddingRepository;

    /**
     * 판매 내역
     * 전체, 진행 중, 종료 건수
     * 퍈매 내역 (상품사진, 상품명, 상품사이즈, 판매단가, 판매상태)
     * 판매 입찰 시간 기준 최신순 정렬
     */
    public SaleHistoryDto getSaleHistory(Long userId) {
        Long allCount = salesBiddingRepository.countAllByUserId(userId);
        List<SalesStatusCountDto> SalesStatusCountDto = salesBiddingRepository.countSalesStatus(userId);
        List<SaleDetailsDto> saleDetailsDTO = salesBiddingRepository.findSaleDetailsByUserId(userId);

        return SaleHistoryDto.builder()
                .allCount(allCount)
                .salesStatusCounts(SalesStatusCountDto)
                .saleDetails(saleDetailsDTO)
                .build();
    }

    public SaleHistoryDto getRecentSaleHistory(Long userId) {
        Long allCount = salesBiddingRepository.countAllByUserId(userId);
        List<SalesStatusCountDto> SalesStatusCountDto = salesBiddingRepository.countSalesStatus(userId);
        List<SaleDetailsDto> saleDetailsDTO = salesBiddingRepository.findRecentSaleDetailsByUserId(userId, PageRequest.of(0, 3));

        return SaleHistoryDto.builder()
                .allCount(allCount)
                .salesStatusCounts(SalesStatusCountDto)
                .saleDetails(saleDetailsDTO)
                .build();
    }

    @Transactional
    public void cancelSalesBidding(Long userId, Long salesBiddingId) {
        SalesBidding salesBidding = salesBiddingRepository.findBySalesBiddingIdAndUserUserId(salesBiddingId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 판매 입찰 내역입니다."));

        salesBidding.changeSalesStatus(SalesStatus.CANCEL);
        salesBiddingRepository.save(salesBidding);
    }
}
