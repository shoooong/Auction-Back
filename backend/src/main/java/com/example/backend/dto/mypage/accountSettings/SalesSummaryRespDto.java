package com.example.backend.dto.mypage.accountSettings;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class SalesSummaryRespDto {
    private BigDecimal totalSalesPrice;
    private Long totalSalesCount;
    private Page<SalesSummaryDto> salesSummaryList;



}
