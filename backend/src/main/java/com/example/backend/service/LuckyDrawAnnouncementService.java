package com.example.backend.service;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.entity.LuckyDrawAnnouncement;

import java.util.List;

public interface LuckyDrawAnnouncementService {

    // 조회
    List<LuckyDrawAnnouncementDto> getAllLuckyDrawAnnouncementList();

    // 등록
    LuckyDrawAnnouncement createLuckyDrawAnnouncement(LuckyDrawAnnouncementDto luckyDrawAnnouncementDto);

    // 수정
    LuckyDrawAnnouncementDto updateLuckyDrawAnnouncement(Long luckyAnnouncementId, LuckyDrawAnnouncementDto luckyDrawAnnouncementDto );

    // 삭제
    void deleteLuckyDrawAnnouncement(final long luckyDrawAnnouncementId);
}
