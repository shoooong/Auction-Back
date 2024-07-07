package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentlyPriceDto {
    private Long latestPrice;
    private LocalDateTime latestDate;
    private Long previousPrice;
    private Double changePercentage;
    private LocalDateTime salesBiddingTime;
    private Long salesBiddingPrice;
    private Long calculationValue;
}
