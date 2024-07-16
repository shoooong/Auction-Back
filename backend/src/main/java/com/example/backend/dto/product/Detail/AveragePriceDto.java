package com.example.backend.dto.product.Detail;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class AveragePriceDto {
    private LocalDateTime contractDateTime;
    private Long averagePrice;

    public AveragePriceDto(LocalDateTime contractDateTime, Long averagePrice) {
        this.contractDateTime = this.contractDateTime;
        this.averagePrice = averagePrice;
    }

    @Override
    public String toString() {
        return "AveragePriceDto{" +
                "contractDateTime=" + contractDateTime +
                ", averagePrice=" + averagePrice +
                '}';
    }
}