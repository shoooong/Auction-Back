package com.example.backend.controller.notice;

import com.example.backend.dto.notice.NoticeDto;
import com.example.backend.entity.Notice;
import com.example.backend.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Log4j2
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    // 공지사항 등록
    @PostMapping("/NoticeRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNotice(@RequestBody NoticeDto noticeDto) {
        Notice createdNotice = noticeService.createNotice(noticeDto);
        log.info("공지사항 생성: {}", createdNotice);
    }

    // 공지사항 조회
    @GetMapping("/NoticeList")
    public List<NoticeDto> getAllNoticeLlist(){

        List<NoticeDto> notices = noticeService.getAllNoticeList();
        return notices;
    }

    // 공지사항 상세 조회
    @GetMapping("/Notice/{noticeId}")
    public NoticeDto getNoticeById(@PathVariable Long noticeId){
        NoticeDto noticeDto = noticeService.getNoticeById(noticeId);
        log.info("공지사항 상세 조회: {}", noticeDto);
        return noticeDto;
    }

    // 공지사항 수정
    @PutMapping("modifyNotice/{noticeId}")
    public NoticeDto updateNotice(@PathVariable Long noticeId, @RequestBody NoticeDto noticeDto) {
        return noticeService.updateNotice(noticeId, noticeDto);
    }

    // 공지사항 삭제
    @DeleteMapping("deleteNotice/{noticeId}")
    public void deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
    }
}
