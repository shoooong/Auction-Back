package com.example.backend.dto.notice;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CombinedNoticeDto {

    private List<NoticeDto> notices;
    private List<LuckyDrawAnnouncementDto> luckyDrawAnnouncements;
}
