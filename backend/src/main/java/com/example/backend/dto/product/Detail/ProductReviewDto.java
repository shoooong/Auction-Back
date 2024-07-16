package com.example.backend.dto.product.Detail;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
    private String modelNum;
    private Long userId;
}
