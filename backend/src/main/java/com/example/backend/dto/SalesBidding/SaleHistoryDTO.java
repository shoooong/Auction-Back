package com.example.backend.dto.SalesBidding;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SaleHistoryDTO {

    private Long allCount;              // 전체
    private Long inspectionCount;       // 검수 중
    private Long processCount;          // 진행 중
    private Long completeCount;         // 종료

    private List<SaleDetailsDTO> saleDetails;       // 상품사진, 상품명, 사이즈, 판매단가, 판매상태

}
