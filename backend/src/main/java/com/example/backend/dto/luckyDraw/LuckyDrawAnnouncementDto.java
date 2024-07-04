package com.example.backend.dto.luckyDraw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LuckyDrawAnnouncementDto {

    private Long luckyAnnouncementId;
    private Long luckyId;
    private String luckyTitle;
    private String luckyContent;
    private String luckyName;
    private String content;
    private String luckySize;
    private String luckyImage;
    private LocalDateTime luckyStartDate;
    private LocalDateTime luckyEndDate;
    private LocalDateTime luckyDate;
    private Integer luckyPeople;
    private Long userId;
    private String email;
    private String nickname;
}
