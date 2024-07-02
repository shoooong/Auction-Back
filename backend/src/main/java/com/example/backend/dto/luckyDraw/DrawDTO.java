package com.example.backend.dto.luckyDraw;

import com.example.backend.entity.Draw;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.LuckyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrawDTO {

    private LuckyStatus luckyStatus;
    private Users user;
    private LuckyDraw luckyDraw;


    /**
     * 응모 POST 요청 결과 응답으로 Entity를 DTO로 변환 후 클라이언트에 전달
     */
    public static DrawDTO fromEntity(Draw draw) {
        return DrawDTO.builder()
                .luckyStatus(draw.getLuckyStatus())
                .user(draw.getUser())
                .luckyDraw(draw.getLuckyDraw())
                .build();
    }
}
