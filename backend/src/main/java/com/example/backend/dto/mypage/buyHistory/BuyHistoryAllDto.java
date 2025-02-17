package com.example.backend.dto.mypage.buyHistory;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BuyHistoryAllDto {

    private Long allCount;          // 전체
    private Long processCount;      // 진행 중
    private Long completeCount;     // 종료

    private List<BuyDetailsDto> buyingDetails;     // 상품사진, 상품명, 사이즈, 결제금액, 주문상태

}
