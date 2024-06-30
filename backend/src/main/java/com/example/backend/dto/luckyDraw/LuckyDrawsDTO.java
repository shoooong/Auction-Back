package com.example.backend.dto.luckyDraw;

import com.example.backend.entity.LuckyDraw;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LuckyDrawsDTO {

    private Long luckyId;
    private String luckyName;
    private String content;
    private String luckyImage;
    private LocalDateTime luckyStartDate;
    private LocalDateTime luckyEndDate;
    private LocalDateTime luckyDate;
    private Integer luckyPeople;


    public static LuckyDrawsDTO fromEntity(LuckyDraw luckyDraw){
        return LuckyDrawsDTO.builder()
                .luckyId(luckyDraw.getLuckyId())
                .luckyName(luckyDraw.getLuckyName())
                .content(luckyDraw.getContent())
                .luckyImage(luckyDraw.getLuckyImage())
                .luckyStartDate(luckyDraw.getLuckyStartDate())
                .luckyEndDate(luckyDraw.getLuckyEndDate())
                .luckyDate(luckyDraw.getLuckyDate())
                .luckyPeople(luckyDraw.getLuckyPeople())
                .build();
    }
}


