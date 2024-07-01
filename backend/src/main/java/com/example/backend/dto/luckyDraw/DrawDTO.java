package com.example.backend.dto.luckyDraw;

import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Draw;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.enumData.LuckyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrawDTO {

    private Long drawId;
    private LuckyStatus luckyStatus;
    private Long userId;
    private Long luckyId;

    /**
     * 사용자가 응모했을 때 정보를 DTO로 받아 Entity로 변환 후 저장
     */
    public static Draw toEntity(DrawDTO drawDTO, UserDTO userDTO, LuckyDraw luckyDraw) {

        return Draw.builder()
                .drawId(drawDTO.getDrawId())
                .luckyStatus(drawDTO.getLuckyStatus())
                .user(userDTO.toEntity())
                .luckyDraw(luckyDraw)
                .build();
    }

    /**
     * 응모 POST 요청 결과 응답으로 Entity를 DTO로 변환 후 클라이언트에 전달
     */
    public static DrawDTO fromEntity(Draw draw) {
        return DrawDTO.builder()
                .drawId(draw.getDrawId())
                .luckyStatus(draw.getLuckyStatus())
                .userId(draw.getUser().getUserId())
                .luckyId(draw.getLuckyDraw().getLuckyId())
                .build();
    }
}
