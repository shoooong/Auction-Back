package com.example.backend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SizePriceDTO {
    private Long sizePriceId;
    private BigDecimal sellPrice;
    private int quantity;
}
