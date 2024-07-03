package com.example.backend.controller.LuckyDrawAnnouncement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
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

    // 조회
    @GetMapping("/luckyDrawAnnouncementList")
    public List<LuckyDrawAnnouncementDto> luckyDrawAnnouncement(){
        List<LuckyDrawAnnouncementDto> announcement = luckyDrawAnnouncementService.getAllLuckyDrawAnnouncementList();
        log.info("조회 완료{}", announcement);
        return announcement;
    }

    //등록
    @PostMapping("/AnnouncementRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLuckyDrawAnnouncement(@RequestBody LuckyDrawAnnouncementDto luckyDrawAnnouncementDto){
        LuckyDrawAnnouncement createLuckyAnnouncement = luckyDrawAnnouncementService.createLuckyDrawAnnouncement(luckyDrawAnnouncementDto);
        log.info("새로운 공지사항 생성: {}", createLuckyAnnouncement);
    }

    // 수정
    @PutMapping("modifyAnnouncement/{announcementId}")
    public LuckyDrawAnnouncementDto updateLuckyDrawAnnouncement(@PathVariable Long announcementId, @RequestBody LuckyDrawAnnouncementDto luckyDrawAnnouncementDto){
        return luckyDrawAnnouncementService.updateLuckyDrawAnnouncement(announcementId, luckyDrawAnnouncementDto);
    }

    //삭제
    @DeleteMapping("deleteAnnouncement/{announcementId}")
    public void deleteLuckyDrawAnnouncement(@PathVariable Long announcementId){
        luckyDrawAnnouncementService.deleteLuckyDrawAnnouncement(announcementId);
    }
}
