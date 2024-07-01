package com.example.backend.service;

import com.example.backend.dto.Announcement.AnnouncementDTO;
import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<AnnouncementDTO> getAllAnnouncementList();

    Announcement createAnnouncement(AnnouncementDTO announcementDTO);

    AnnouncementDTO updateAnnouncement(Long announcementId, AnnouncementDTO announcementDTO);

    void deleteAnnouncement(final long announcementId);

    AnnouncementDTO getAnnouncementById(Long announcementId);
}
