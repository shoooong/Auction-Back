package com.example.backend.dto.product.Detail;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidRequestDto {
    private String productSize;
    private String type;
    private String modelNum;
    private Long userId;
}
