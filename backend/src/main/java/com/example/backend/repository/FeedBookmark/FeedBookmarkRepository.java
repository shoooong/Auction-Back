package com.example.backend.repository.FeedBookmark;

import com.example.backend.entity.FeedBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBookmarkRepository extends JpaRepository<FeedBookmark, Long> {
    List<FeedBookmark> findByStyleFeed_FeedId(Long feedId);
    List<FeedBookmark> findByUser_UserId(Long userId);
    boolean existsByUser_UserIdAndStyleFeed_FeedId(Long userId, Long feedId);
}
