package com.example.backend.repository.LuckyDraw;

import com.example.backend.entity.LuckyDrawAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LuckyDrawAnnouncementRepository extends JpaRepository<LuckyDrawAnnouncement, Long> {
}
