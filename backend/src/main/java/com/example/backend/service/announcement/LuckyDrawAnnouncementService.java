package com.example.backend.service.announcement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.entity.LuckyDrawAnnouncement;

import java.util.List;

public interface LuckyDrawAnnouncementService {

    // 조회
    List<LuckyDrawAnnouncementDto> getAllLuckyDrawAnnouncementList();

    // 상세 조회
    LuckyDrawAnnouncementDto findLuckyDrawAnnouncementById(Long luckyAnnouncementId);

    // 등록
    LuckyDrawAnnouncement createLuckyDrawAnnouncement(LuckyDrawAnnouncementDto luckyDrawAnnouncementDto);

    // 조회-관리자
    List<LuckyDrawAnnouncementDto> getAllAdminLuckyDrawAnnouncementList();

    // 상세조회-관리자
    LuckyDrawAnnouncementDto findAdminLuckyDrawAnnouncementById(Long luckyAnnouncementId);

    // 수정
    LuckyDrawAnnouncementDto updateLuckyDrawAnnouncement(Long luckyAnnouncementId, LuckyDrawAnnouncementDto luckyDrawAnnouncementDto);

    // 삭제
    void deleteLuckyDrawAnnouncement(final long luckyDrawAnnouncementId);
}
