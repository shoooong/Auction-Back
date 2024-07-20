package com.example.backend.dto.mypage.main;

import com.example.backend.dto.mypage.buyHistory.BuyHistoryAllDto;
import com.example.backend.dto.mypage.saleHistory.SaleHistoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MypageMainDto {

    private ProfileDto profileDto;
    private Long couponCount;
    private BuyHistoryAllDto buyHistoryAllDto;
    private SaleHistoryDto saleHistoryDto;

    private List<BookmarkProductsDto> bookmarkProductsDto;
}
