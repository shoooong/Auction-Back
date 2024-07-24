package com.example.backend.dto.bookmarkproduct;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkProductDto {
    private Long userId;
    private Long productId;
    private String productImg;
    private String modelNum;
    private String productSize;
}
