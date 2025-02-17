package com.example.backend.dto.mypage.drawHistory;

import com.example.backend.entity.enumData.LuckyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DrawDetailsDto {

    private String luckyImg;
    private String luckyName;
    private String luckySize;
    private LocalDateTime luckyDate;

    private LuckyStatus luckyStatus;

    public DrawDetailsDto(String luckyImg, String luckyName, String luckySize, LocalDateTime luckyDate, LuckyStatus luckyStatus) {
        this.luckyImg = luckyImg;
        this.luckyName = luckyName;
        this.luckySize = luckySize;
        this.luckyDate = luckyDate;
        this.luckyStatus = luckyStatus;
    }

}
