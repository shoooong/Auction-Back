package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AveragePriceResponseDto {
    private List<AveragePriceDto> threeDayPrices;
    private List<AveragePriceDto> oneMonthPrices;
    private List<AveragePriceDto> sixMonthPrices;
    private List<AveragePriceDto> oneYearPrices;
    private List<AveragePriceDto> TotalExecutionPrice;
}
