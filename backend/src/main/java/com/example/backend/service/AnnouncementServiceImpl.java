package com.example.backend.service;

import com.example.backend.dto.Announcement.AnnouncementDTO;
import com.example.backend.entity.Announcement;
import com.example.backend.entity.User;
import com.example.backend.repository.Announcement.AnnouncementRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AnnouncementServiceImpl  implements AnnouncementService{

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;


    // 공지사항 조회
    @Override
    public List<AnnouncementDTO> getAllAnnouncementList(){

        List<Announcement> announcements = announcementRepository.findAllByOrderByCreateDateDesc();


        return announcements.stream()
                .map(announcement -> new AnnouncementDTO(
                        announcement.getAnnouncementId(),
                        announcement.getAnnounceTitle(),
                        announcement.getAnnounceContent(),
                        announcement.getCreateDate(),
                        announcement.getModifyDate(),
                        announcement.getUser().getUserId()
                )).collect(Collectors.toList());
    }

    // 공지사항 상세 조회
    @Override
    public AnnouncementDTO getAnnouncementById(Long announcementId){
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

return new AnnouncementDTO(
        announcement.getAnnouncementId(),
        announcement.getAnnounceTitle(),
        announcement.getAnnounceContent(),
        announcement.getCreateDate(),
        announcement.getModifyDate(),
        announcement.getUser().getUserId()
);
    }

    // 공지사항 등록
    @Override
    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) {

        User user = userRepository.findById(announcementDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Announcement announcement = new Announcement();
        announcement.setAnnounceTitle(announcementDTO.getAnnouncementTitle());
        announcement.setAnnounceContent(announcementDTO.getAnnouncementContent());
        announcement.setUser(user);

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return savedAnnouncement;
    }

    // 공지사항 수정
    @Override
    public AnnouncementDTO updateAnnouncement(Long announcementId, AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        if (announcementDTO.getAnnouncementTitle() != null) {
            announcement.setAnnounceTitle(announcementDTO.getAnnouncementTitle());
        }
        if (announcementDTO.getAnnouncementContent() != null) {
            announcement.setAnnounceContent(announcementDTO.getAnnouncementContent());
        }
        Announcement updateAnnouncement = announcementRepository.save(announcement);

        return new AnnouncementDTO(
                updateAnnouncement.getAnnouncementId(),
                updateAnnouncement.getAnnounceTitle(),
                updateAnnouncement.getAnnounceContent(),
                updateAnnouncement.getCreateDate(),
                updateAnnouncement.getModifyDate(),
                updateAnnouncement.getUser().getUserId()
        );
    }

    // 공지사항 삭제
    @Override
    public void deleteAnnouncement(final long announcementId) {
        announcementRepository.deleteById(announcementId);
    }
}
