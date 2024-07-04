package com.example.backend.service.announcement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementListDto;
import com.example.backend.entity.LuckyDrawAnnouncement;

import java.util.List;

public interface LuckyDrawAnnouncementService {

    // 조회
    List<LuckyDrawAnnouncementListDto> getAllLuckyDrawAnnouncementList();

    // 상세 조회
    LuckyDrawAnnouncementDto findLuckyDrawAnnouncementById(Long luckyAnnouncementId);

    // 등록
    LuckyDrawAnnouncement createLuckyDrawAnnouncement(LuckyDrawAnnouncementListDto luckyDrawAnnouncementListDto);

    // 수정
    LuckyDrawAnnouncementListDto updateLuckyDrawAnnouncement(Long luckyAnnouncementId, LuckyDrawAnnouncementListDto luckyDrawAnnouncementListDto);

    // 삭제
    void deleteLuckyDrawAnnouncement(final long luckyDrawAnnouncementId);
}
