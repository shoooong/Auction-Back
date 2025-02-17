package com.example.backend.dto.luckyDraw;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LuckyDrawAnnouncementDto {

    private Long luckyAnnouncementId;
    private Long luckyId;
    private String luckyTitle;
    private String luckyContent;
}
