package com.example.backend.dto.product.Detail;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoRequestDto {
    private Long reviewId;
    private Long userId;
    private String modelNum;
    private String reviewImg;
    private String reviewContent;
}
