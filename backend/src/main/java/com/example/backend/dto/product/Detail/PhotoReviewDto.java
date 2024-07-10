package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoReviewDto {
    private Long userId;
    private String reviewImg;
    private String reviewContent;
    private int reviewLike;
}
