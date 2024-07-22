package com.example.backend.service.announcement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.LuckyDrawAnnouncement;
import com.example.backend.repository.LuckyDraw.DrawRepository;
import com.example.backend.repository.LuckyDraw.LuckyDrawAnnouncementRepository;
import com.example.backend.repository.LuckyDraw.LuckyDrawRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class LuckyDrawAnnouncementServiceImpl implements LuckyDrawAnnouncementService {

    @Autowired
    private LuckyDrawAnnouncementRepository luckyDrawAnnouncementRepository;

    @Autowired
    private LuckyDrawRepository luckyDrawRepository;

    @Autowired
    private DrawRepository drawRepository;

    @Autowired
    private UserRepository userRepository;

    // 이벤트 공지사항 조회
    @Override
    public List<LuckyDrawAnnouncementDto> getAllLuckyDrawAnnouncementList() {
        List<LuckyDrawAnnouncement> luckyDrawAnnouncements = luckyDrawAnnouncementRepository.findAll();

        return luckyDrawAnnouncements.stream()
                .map(luckyDrawAnnouncement -> new LuckyDrawAnnouncementDto(
                        luckyDrawAnnouncement.getLuckyAnnouncementId(),
                        luckyDrawAnnouncement.getLuckyDraw().getLuckyId(),
                        luckyDrawAnnouncement.getLuckyTitle(),
                        luckyDrawAnnouncement.getLuckyContent()
                ))
                .collect(Collectors.toList());
    }

    // 이벤트 공지사항 상세 조회
    @Override
    public LuckyDrawAnnouncementDto findLuckyDrawAnnouncementById(Long luckyAnnouncementId) {
        LuckyDrawAnnouncement luckyDrawAnnouncement = luckyDrawAnnouncementRepository.findById(luckyAnnouncementId)
                .orElseThrow(() -> new RuntimeException("Lucky Draw Announcement Not Found"));

        LuckyDraw luckyDraw = luckyDrawAnnouncement.getLuckyDraw();

        return LuckyDrawAnnouncementDto.builder()
                .luckyAnnouncementId(luckyDrawAnnouncement.getLuckyAnnouncementId())
                .luckyId(luckyDraw.getLuckyId())
                .luckyTitle(luckyDrawAnnouncement.getLuckyTitle())
                .luckyContent(luckyDrawAnnouncement.getLuckyContent())
                .build();
    }

    // 관리자용 메서드 구현
    @Override
    public List<LuckyDrawAnnouncementDto> getAllAdminLuckyDrawAnnouncementList() {
        // 관리자 전용 로직을 추가할 수 있습니다.
        List<LuckyDrawAnnouncement> luckyDrawAnnouncements = luckyDrawAnnouncementRepository.findAll();
        return luckyDrawAnnouncements.stream()
                .map(luckyDrawAnnouncement -> new LuckyDrawAnnouncementDto(
                        luckyDrawAnnouncement.getLuckyAnnouncementId(),
                        luckyDrawAnnouncement.getLuckyDraw().getLuckyId(),
                        luckyDrawAnnouncement.getLuckyTitle(),
                        luckyDrawAnnouncement.getLuckyContent()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public LuckyDrawAnnouncementDto findAdminLuckyDrawAnnouncementById(Long luckyAnnouncementId) {
        // 관리자 전용 로직을 추가할 수 있습니다.
        LuckyDrawAnnouncement luckyDrawAnnouncement = luckyDrawAnnouncementRepository.findById(luckyAnnouncementId)
                .orElseThrow(() -> new RuntimeException("Lucky Draw Announcement Not Found"));

        LuckyDraw luckyDraw = luckyDrawAnnouncement.getLuckyDraw();

        return LuckyDrawAnnouncementDto.builder()
                .luckyAnnouncementId(luckyDrawAnnouncement.getLuckyAnnouncementId())
                .luckyId(luckyDraw.getLuckyId())
                .luckyTitle(luckyDrawAnnouncement.getLuckyTitle())
                .luckyContent(luckyDrawAnnouncement.getLuckyContent())
                .build();
    }

    // 이벤트 공지사항 등록
    @Override
    public LuckyDrawAnnouncement createLuckyDrawAnnouncement(LuckyDrawAnnouncementDto luckyDrawAnnouncementDto) {
        LuckyDraw luckyDraw = luckyDrawRepository.findById(luckyDrawAnnouncementDto.getLuckyId())
                .orElseThrow(() -> new RuntimeException("Lucky Draw Not Found"));

        LuckyDrawAnnouncement luckyDrawAnnouncement = new LuckyDrawAnnouncement();
        luckyDrawAnnouncement.setLuckyTitle(luckyDrawAnnouncementDto.getLuckyTitle());
        luckyDrawAnnouncement.setLuckyContent(luckyDrawAnnouncementDto.getLuckyContent());
        luckyDrawAnnouncement.setLuckyDraw(luckyDraw);

        LuckyDrawAnnouncement saved = luckyDrawAnnouncementRepository.save(luckyDrawAnnouncement);
        log.info(saved.toString());

        return saved;
    }

    @Override
    public LuckyDrawAnnouncementDto updateLuckyDrawAnnouncement(Long luckyAnnouncementId, LuckyDrawAnnouncementDto luckyDrawAnnouncementDto) {
        LuckyDrawAnnouncement luckyDrawAnnouncement = luckyDrawAnnouncementRepository.findById(luckyAnnouncementId)
                .orElseThrow(() -> new RuntimeException("Lucky Draw Announcement Not Found"));

        luckyDrawAnnouncement.setLuckyTitle(luckyDrawAnnouncementDto.getLuckyTitle());
        luckyDrawAnnouncement.setLuckyContent(luckyDrawAnnouncementDto.getLuckyContent());

        LuckyDrawAnnouncement updated = luckyDrawAnnouncementRepository.save(luckyDrawAnnouncement);
        return new LuckyDrawAnnouncementDto(
                updated.getLuckyAnnouncementId(),
                updated.getLuckyDraw().getLuckyId(),
                updated.getLuckyTitle(),
                updated.getLuckyContent()
        );
    }

    // 이벤트 공지사항 삭제
    @Override
    public void deleteLuckyDrawAnnouncement(final long luckyAnnouncementId) {
        luckyDrawAnnouncementRepository.deleteById(luckyAnnouncementId);
    }
}
