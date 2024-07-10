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
    private List<AveragePriceDto> oneDayPrices;
    private List<AveragePriceDto> threeDayPrices;
    private List<AveragePriceDto> sevenDayPrices;
    private List<AveragePriceDto> fifteenDayPrices;
    private List<AveragePriceDto> thirtyDayPrices;
}
