package com.example.backend.dto.product.Detail;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoReviewDto {
    private Long userId;
    private String reviewImg;
    private String reviewContent;
    private int reviewLike;
}
