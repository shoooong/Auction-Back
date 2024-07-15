package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyingBidRequestDto {
    private String productSize;
    private String type;
    private String modelNum;
    private Long userId;
}
