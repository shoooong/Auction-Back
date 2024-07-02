package com.example.backend.service;

import com.example.backend.dto.Notice.NoticeDto;
import com.example.backend.entity.Notice;
import com.example.backend.entity.Users;
import com.example.backend.repository.Notice.NoticeRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;


    // 공지사항 조회
    @Override
    public List<NoticeDto> getAllNoticeList(){

        List<Notice> notices = noticeRepository.findAllByOrderByCreateDateDesc();


        return notices.stream()
                .map(notice -> new NoticeDto(
                        notice.getNoticeId(),
                        notice.getNoticeTitle(),
                        notice.getNoticeContent(),
                        notice.getCreateDate(),
                        notice.getModifyDate(),
                        notice.getUser().getUserId()
                )).collect(Collectors.toList());
    }

    // 공지사항 상세 조회
    @Override
    public NoticeDto getNoticeById(Long noticeId){
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        return new NoticeDto(
                notice.getNoticeId(),
                notice.getNoticeTitle(),
                notice.getNoticeContent(),
                notice.getCreateDate(),
                notice.getModifyDate(),
                notice.getUser().getUserId()
        );
    }

    // 공지사항 등록
    @Override
    public Notice createNotice(NoticeDto noticeDto) {

        Users user = userRepository.findById(noticeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notice notice = new Notice();
        notice.setNoticeTitle(noticeDto.getNoticeTitle());
        notice.setNoticeContent(noticeDto.getNoticeContent());
        notice.setUser(user);

        return noticeRepository.save(notice);
    }

    // 공지사항 수정
    @Override
    public NoticeDto updateNotice(Long noticeId, NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("notice not found"));

        if (noticeDto.getNoticeTitle() != null) {
            notice.setNoticeTitle(noticeDto.getNoticeTitle());
        }
        if (noticeDto.getNoticeContent() != null) {
            notice.setNoticeContent(noticeDto.getNoticeContent());
        }
        Notice updatenotice = noticeRepository.save(notice);

        return new NoticeDto(
                updatenotice.getNoticeId(),
                updatenotice.getNoticeTitle(),
                updatenotice.getNoticeContent(),
                updatenotice.getCreateDate(),
                updatenotice.getModifyDate(),
                updatenotice.getUser().getUserId()
        );
    }

    // 공지사항 삭제
    @Override
    public void deleteNotice(final long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}
