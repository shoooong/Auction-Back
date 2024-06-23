package com.example.backend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDTO {
    private Long sizeId;
    private String productSize;
    private List<SizePriceDTO> sizePrices;
    private List<BidDTO> bids;
}
