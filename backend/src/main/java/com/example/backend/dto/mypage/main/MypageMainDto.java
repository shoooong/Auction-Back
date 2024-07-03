package com.example.backend.dto.mypage.main;

import com.example.backend.dto.mypage.buyHistory.BuyHistoryDto;
import com.example.backend.dto.mypage.saleHistory.SaleHistoryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MypageMainDto {

    private ProfileDto profileDto;
    private Long couponCount;
    private BuyHistoryDto buyHistoryDto;
    private SaleHistoryDto saleHistoryDto;

    // TODO: 관심상품Dto 추가
    // private LikeProductsDto likeProductsDto;
}
