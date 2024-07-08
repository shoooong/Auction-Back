package com.example.backend.dto.notice;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementListDto;
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
    private List<LuckyDrawAnnouncementListDto> luckyDrawAnnouncements;
}
