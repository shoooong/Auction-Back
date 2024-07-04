package com.example.backend.controller.announcement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementListDto;
import com.example.backend.entity.LuckyDrawAnnouncement;
import com.example.backend.service.announcement.LuckyDrawAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LuckyDrawAnnouncementController {

    @Autowired
    private LuckyDrawAnnouncementService luckyDrawAnnouncementService;

    // 이벤트 공지사항 조회
    @GetMapping("/luckyDrawAnnouncementList")
    public List<LuckyDrawAnnouncementListDto> luckyDrawAnnouncementList(){
        List<LuckyDrawAnnouncementListDto> announcement = luckyDrawAnnouncementService.getAllLuckyDrawAnnouncementList();
        log.info("조회 완료{}", announcement);
        return announcement;
    }

    // 이벤트 공지사항 상세 조회
    @GetMapping("/luckyDrawAnnouncement/{announcementId}")
    public LuckyDrawAnnouncementDto luckyDrawAnnouncement(@PathVariable Long announcementId){
        LuckyDrawAnnouncementDto luckyDrawAnnouncementDto = luckyDrawAnnouncementService.findLuckyDrawAnnouncementById(announcementId);
        log.info("이벤트 공지사항 상세조회 완료: {}", luckyDrawAnnouncementDto);
        return luckyDrawAnnouncementDto;
    }

    // 이벤트 공지사항 등록
    @PostMapping("/announcementRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLuckyDrawAnnouncement(@RequestBody LuckyDrawAnnouncementListDto luckyDrawAnnouncementListDto){
        LuckyDrawAnnouncement createLuckyAnnouncement = luckyDrawAnnouncementService.createLuckyDrawAnnouncement(luckyDrawAnnouncementListDto);
        log.info("새로운 공지사항 생성: {}", createLuckyAnnouncement);
    }

    // 이벤트 공지사항 수정
    @PutMapping("modifyAnnouncement/{announcementId}")
    public LuckyDrawAnnouncementListDto updateLuckyDrawAnnouncement(@PathVariable Long announcementId, @RequestBody LuckyDrawAnnouncementListDto luckyDrawAnnouncementListDto){
        return luckyDrawAnnouncementService.updateLuckyDrawAnnouncement(announcementId, luckyDrawAnnouncementListDto);
    }

    // 이벤트 공지사항 삭제
    @DeleteMapping("deleteAnnouncement/{announcementId}")
    public void deleteLuckyDrawAnnouncement(@PathVariable Long announcementId){
        luckyDrawAnnouncementService.deleteLuckyDrawAnnouncement(announcementId);
    }
}
