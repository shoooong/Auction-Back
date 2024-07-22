package com.example.backend.controller.announcement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.LuckyDrawAnnouncement;
import com.example.backend.service.announcement.LuckyDrawAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LuckyDrawAnnouncementController {

    @Autowired
    private LuckyDrawAnnouncementService luckyDrawAnnouncementService;

    // 이벤트 공지사항 등록
    @PostMapping("/announcementRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public LuckyDrawAnnouncement createLuckyDrawAnnouncement(
            @RequestBody LuckyDrawAnnouncementDto luckyDrawAnnouncementDto,
            @AuthenticationPrincipal UserDTO userDTO) {

        if (!userDTO.isRole()) {
            throw new RuntimeException("Only administrators can register announcements");
        }

        LuckyDrawAnnouncement createLuckyAnnouncement = luckyDrawAnnouncementService.createLuckyDrawAnnouncement(luckyDrawAnnouncementDto);
        log.info("새로운 공지사항 생성: {}", createLuckyAnnouncement);
        return createLuckyAnnouncement;
    }

    // 이벤트 공지사항 조회
    @GetMapping("/luckyDrawAnnouncementList")
    public List<LuckyDrawAnnouncementDto> luckyDrawAnnouncementList(){
        List<LuckyDrawAnnouncementDto> announcement = luckyDrawAnnouncementService.getAllLuckyDrawAnnouncementList();
        log.info("조회 완료{}", announcement);
        return announcement;
    }

    // 이벤트 공지사항 상세조회
    @GetMapping("/luckyDrawAnnouncement/{announcementId}")
    public LuckyDrawAnnouncementDto luckyDrawAnnouncement(@PathVariable Long announcementId){
        LuckyDrawAnnouncementDto luckyDrawAnnouncementDto = luckyDrawAnnouncementService.findLuckyDrawAnnouncementById(announcementId);
        log.info("이벤트 공지사항 상세조회 완료: {}", luckyDrawAnnouncementDto);
        return luckyDrawAnnouncementDto;
    }
    // 관리자용 이벤트 공지사항 조회
    @GetMapping("/admin/luckyDrawAnnouncementList")
    public List<LuckyDrawAnnouncementDto> adminLuckyDrawAnnouncementList(){
        List<LuckyDrawAnnouncementDto> announcement = luckyDrawAnnouncementService.getAllAdminLuckyDrawAnnouncementList();
        log.info("관리자 조회 완료{}", announcement);
        return announcement;
    }

    // 관리자용 이벤트 공지사항 상세조회
    @GetMapping("/admin/luckyDrawAnnouncement/{announcementId}")
    public LuckyDrawAnnouncementDto adminLuckyDrawAnnouncement(@PathVariable Long announcementId){
        LuckyDrawAnnouncementDto luckyDrawAnnouncementDto = luckyDrawAnnouncementService.findAdminLuckyDrawAnnouncementById(announcementId);
        log.info("관리자 이벤트 공지사항 상세조회 완료: {}", luckyDrawAnnouncementDto);
        return luckyDrawAnnouncementDto;
    }

    // 이벤트 공지사항 수정
    @PutMapping("/modifyAnnouncement/{announcementId}")
    public LuckyDrawAnnouncementDto updateLuckyDrawAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody LuckyDrawAnnouncementDto luckyDrawAnnouncementDto,
            @AuthenticationPrincipal UserDTO userDTO) {

        if (!userDTO.isRole()) {
            throw new RuntimeException("Only administrators can modify announcements");
        }

        return luckyDrawAnnouncementService.updateLuckyDrawAnnouncement(announcementId, luckyDrawAnnouncementDto);
    }

    // 이벤트 공지사항 삭제
    @DeleteMapping("/deleteAnnouncement/{announcementId}")
    public void deleteLuckyDrawAnnouncement(@PathVariable Long announcementId,
                                            @AuthenticationPrincipal UserDTO userDTO) {

        if (!userDTO.isRole()) {
            throw new RuntimeException("Only administrators can delete announcements");
        }

        luckyDrawAnnouncementService.deleteLuckyDrawAnnouncement(announcementId);
    }
}
