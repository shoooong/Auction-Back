package com.example.backend.dto.admin;

import com.example.backend.entity.LuckyDraw;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiListUI;
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
        private String luckySize;
        private Integer luckyPeople;
        private MultipartFile luckyphoto;

        public AdminLuckDrawDto(LuckyDraw luckyDraw) {
            this.luckyName = luckyDraw.getLuckyName();
            this.content = luckyDraw.getContent();
            this.luckyImage = luckyDraw.getLuckyImage();
            this.luckySize = luckyDraw.getLuckySize();
            this.luckyPeople = luckyDraw.getLuckyPeople();
        }
    }
}
