package com.example.backend.dto.mypage.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkProductsDto {

    // 사진, 브랜드, 상품명, 모델번호
    private ProductDetailsDto productDetailsDto;

    // 즉시구매가
    private Long nowLowPrice;

}
