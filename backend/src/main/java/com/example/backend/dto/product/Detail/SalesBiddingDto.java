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
public class SalesBiddingDto {

    private Long productId;
    private String modelNum;
    private String productSize;
    private Long latestPrice;
    private Long previousPrice;
    private Double previousPercentage;
    private LocalDateTime salesBiddingTime;
    private Long salesBiddingPrice;
}
