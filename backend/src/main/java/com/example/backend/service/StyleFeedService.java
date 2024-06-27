package com.example.backend.service;

import com.example.backend.dto.feed.FeedBookmarkDTO;
import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.StyleFeed;

import java.util.List;

public interface StyleFeedService {

    List<StyleFeedDTO> getAllStyleFeedList();

    List<StyleFeedDTO> getAllStyleFeedRanking();

    StyleFeedDTO getStyleFeedById(Long id);

    StyleFeed createStyleFeed(StyleFeedDTO styleFeedDTO);

    StyleFeedDTO updateStyleFeed(Long feedId, StyleFeedDTO styleFeedDTO);

    void deleteStyleFeed(Long feedId);

    void deleteFeedBookmark(Long styleSaveId);

    List<FeedBookmarkDTO> getAllFeedBookmarks();

    FeedBookmarkDTO createFeedBookmark(FeedBookmarkDTO feedBookmarkDTO);
}
