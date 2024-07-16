package com.example.backend.dto.product.Detail;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AveragePriceResponseDto {
    private List<AveragePriceDto> threeDayPrices;
    private List<AveragePriceDto> oneMonthPrices;
    private List<AveragePriceDto> sixMonthPrices;
    private List<AveragePriceDto> oneYearPrices;
    private List<AveragePriceDto> TotalExecutionPrice;
}
