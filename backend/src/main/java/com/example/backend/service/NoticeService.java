package com.example.backend.service;



import com.example.backend.dto.Notice.NoticeDto;
import com.example.backend.entity.Notice;

import java.util.List;

public interface NoticeService {

    List<NoticeDto> getAllNoticeList();

    Notice createNotice(NoticeDto noticeDTO);

    NoticeDto updateNotice(Long noticeId, NoticeDto noticeDTO);

    void deleteNotice(final long noticeId);

    NoticeDto getNoticeById(Long noticeId);
}
