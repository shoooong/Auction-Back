package com.example.backend.repository.FeedBookmark;

import com.example.backend.entity.FeedBookmark;
import com.example.backend.entity.QStyleFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBookmarkRepository extends JpaRepository<FeedBookmark, Long> {
    List<FeedBookmark> findByStyleFeed(QStyleFeed styleFeed);
}
