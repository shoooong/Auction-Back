package com.example.backend.service.announcement;

import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementDto;
import com.example.backend.dto.luckyDraw.LuckyDrawAnnouncementListDto;
import com.example.backend.entity.Draw;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.LuckyDrawAnnouncement;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.LuckyStatus;
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
    public List<LuckyDrawAnnouncementListDto> getAllLuckyDrawAnnouncementList() {
        List<LuckyDrawAnnouncement> luckyDrawAnnouncements = luckyDrawAnnouncementRepository.findAll();

        return luckyDrawAnnouncements.stream()
                .map(luckyDrawAnnouncement -> new LuckyDrawAnnouncementListDto(
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

        Draw draw = drawRepository.findByLuckyDrawAndLuckyStatus(luckyDraw, LuckyStatus.LUCKY);

        Long userId = (draw != null) ? draw.getUser().getUserId() : null;

        String email = null;
        String nickname = null;

        if (userId != null) {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            email = user.getEmail();
            nickname = user.getNickname();
        }

        return LuckyDrawAnnouncementDto.builder()
                .luckyAnnouncementId(luckyDrawAnnouncement.getLuckyAnnouncementId())
                .luckyId(luckyDraw.getLuckyId())
                .luckyTitle(luckyDrawAnnouncement.getLuckyTitle())
                .luckyContent(luckyDrawAnnouncement.getLuckyContent())
                .luckyName(luckyDraw.getLuckyName())
                .content(luckyDraw.getContent())
                .luckySize(luckyDraw.getLuckySize())
                .luckyImage(luckyDraw.getLuckyImage())
                .luckyStartDate(luckyDraw.getLuckyStartDate())
                .luckyEndDate(luckyDraw.getLuckyEndDate())
                .luckyDate(luckyDraw.getLuckyDate())
                .luckyPeople(luckyDraw.getLuckyPeople())
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .build();
    }

    // 이벤트 공지사항 등록
    @Override
    public LuckyDrawAnnouncement createLuckyDrawAnnouncement(LuckyDrawAnnouncementListDto luckyDrawAnnouncementListDto) {
        LuckyDraw luckyDraw = luckyDrawRepository.findById(luckyDrawAnnouncementListDto.getLuckyId())
                .orElseThrow(() -> new RuntimeException("Lucky Draw Not Found"));

        LuckyDrawAnnouncement luckyDrawAnnouncement = new LuckyDrawAnnouncement();
        luckyDrawAnnouncement.setLuckyTitle(luckyDrawAnnouncementListDto.getLuckyTitle());
        luckyDrawAnnouncement.setLuckyContent(luckyDrawAnnouncementListDto.getLuckyContent());
        luckyDrawAnnouncement.setLuckyDraw(luckyDraw);

        LuckyDrawAnnouncement saved = luckyDrawAnnouncementRepository.save(luckyDrawAnnouncement);
        log.info(saved.toString());

        return saved;
    }

    @Override
    public LuckyDrawAnnouncementListDto updateLuckyDrawAnnouncement(Long luckyAnnouncementId, LuckyDrawAnnouncementListDto luckyDrawAnnouncementListDto) {
        LuckyDrawAnnouncement luckyDrawAnnouncement = luckyDrawAnnouncementRepository.findById(luckyAnnouncementId)
                .orElseThrow(() -> new RuntimeException("Lucky Draw Announcement Not Found"));

        luckyDrawAnnouncement.setLuckyTitle(luckyDrawAnnouncementListDto.getLuckyTitle());
        luckyDrawAnnouncement.setLuckyContent(luckyDrawAnnouncementListDto.getLuckyContent());

        LuckyDrawAnnouncement updated = luckyDrawAnnouncementRepository.save(luckyDrawAnnouncement);
        return new LuckyDrawAnnouncementListDto(
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
