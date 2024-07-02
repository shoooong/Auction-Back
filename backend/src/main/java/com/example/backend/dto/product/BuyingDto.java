package com.example.backend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyingDto {
    private Long buyingId;
    private LocalDateTime buyingBiddingTime;
    private Long buyingBiddingPrice;
    private Long buyingProductPrice;
}
