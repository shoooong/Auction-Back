package com.example.backend.service;

import com.example.backend.dto.feed.FeedBookmarkDTO;
import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.StyleFeed;

import java.util.List;

public interface StyleFeedService {

    // 최신순으로 피드 조회
    List<StyleFeedDTO> getAllStyleFeedList();

    // 좋아요순으로 피드 조회
    List<StyleFeedDTO> getAllStyleFeedRanking();

    // 피드 상세 조회
    StyleFeedDTO getStyleFeedById(Long feedId);

    // 피드 등록
    StyleFeed createStyleFeed(StyleFeedDTO styleFeedDTO);

    // 피드 수정
    StyleFeedDTO updateStyleFeed(Long feedId, StyleFeedDTO styleFeedDTO);

    // 피드 삭제
    void deleteStyleFeed(final long feedId);

    // 관심피드 조회
    List<FeedBookmarkDTO> getAllFeedBookmarks();

    // 관심피드 등록
    FeedBookmarkDTO createFeedBookmark(FeedBookmarkDTO feedBookmarkDTO);

    // 관심피드 삭제
    void deleteFeedBookmark(final long styleSavedId);
}
