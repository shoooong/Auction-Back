package com.example.backend.repository.Announcement;

import com.example.backend.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Notice, Long> {
}
