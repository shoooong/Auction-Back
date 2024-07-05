package com.example.backend.dto.admin;

import com.example.backend.entity.LuckyDraw;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

 // 기본 생성자 추가
public class AdminReqDto {

     @Getter
     @Setter
     @NoArgsConstructor
    public static class AdminLuckDrawDto {
        private String luckyName;
        private String content;
        private String luckyImage;

//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//        private LocalDateTime luckyStartDate;
//
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//        private LocalDateTime luckyEndDate;
//
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//        private LocalDateTime luckyDate;

        private String luckySize;

        private Integer luckyPeople;



        public AdminLuckDrawDto(LuckyDraw luckyDraw) {
            this.luckyName = luckyDraw.getLuckyName();
            this.content = luckyDraw.getContent();
            this.luckyImage = luckyDraw.getLuckyImage();
            this.luckySize = luckyDraw.getLuckySize();
            this.luckyPeople = luckyDraw.getLuckyPeople();
        }
    }
}
