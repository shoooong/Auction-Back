package com.example.backend.dto.product.Detail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class AveragePriceDto {
    private String dateTime;
    private Double averagePrice;

    public AveragePriceDto(String dateTime, Double averagePrice) {
        this.dateTime = dateTime;
        this.averagePrice = averagePrice;
    }
}