package com.example.backend.service;

import com.example.backend.dto.SalesBidding.SaleDetailsDTO;
import com.example.backend.dto.SalesBidding.SaleHistoryDTO;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public SaleHistoryDTO getSaleHistory(Long userId) {
        Long allCount = salesBiddingRepository.countAllByUserId(userId);
        Long inspectionCount = salesBiddingRepository.countInspectionByUserId(userId);
        Long processCount = salesBiddingRepository.countProcessByUserId(userId);
        Long completeCount = salesBiddingRepository.countCompleteByUserId(userId);

        List<SaleDetailsDTO> saleDetailsDTO = salesBiddingRepository.findSaleDetailsByUserId(userId);

        return SaleHistoryDTO.builder()
                .allCount(allCount)
                .inspectionCount(inspectionCount)
                .processCount(processCount)
                .completeCount(completeCount)
                .saleDetails(saleDetailsDTO)
                .build();
    }
}
