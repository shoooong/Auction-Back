package com.example.backend.controller.Announcement;

import com.example.backend.dto.Announcement.AnnouncementDTO;
import com.example.backend.entity.Announcement;
import com.example.backend.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Log4j2
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;


    // 공지사항 등록
    @PostMapping("/AnnouncementRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        Announcement createdAnnouncement = announcementService.createAnnouncement(announcementDTO);
        log.info("공지사항 생성: {}", createdAnnouncement);
    }

    // 공지사항 조회
    @GetMapping("/AnnouncementList")
    public List<AnnouncementDTO> getAllAnnouncementLlist(){

        List<AnnouncementDTO> announcements = announcementService.getAllAnnouncementList();
        return announcements;
    }

    @GetMapping("/Announcement/{announcementId}")
    public AnnouncementDTO getAnnouncementById(@PathVariable Long announcementId){
        AnnouncementDTO announcementDTO = announcementService.getAnnouncementById(announcementId);
        log.info("공지사항 상세 조회: {}", announcementDTO);
        return announcementDTO;
    }

    // 공지사항 수정
    @PutMapping("modifyAnnouncement/{announcementId}")
    public AnnouncementDTO updateAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementDTO announcementDTO) {
        return announcementService.updateAnnouncement(announcementId, announcementDTO);
    }

    // 공지사항 삭제
    @DeleteMapping("deleteAnnouncement/{announcementId}")
    public void deleteAnnouncement(@PathVariable Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);
    }
}
