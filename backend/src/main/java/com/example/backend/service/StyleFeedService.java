package com.example.backend.service;

import com.example.backend.dto.feed.FeedBookmarkDTO;
import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.StyleFeed;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StyleFeedService {

    List<StyleFeedDTO> getAllStyleFeedList();

    List<StyleFeedDTO> getAllStyleFeedRanking();

    StyleFeedDTO getStyleFeedById(Long id);

    StyleFeed createStyleFeed(StyleFeedDTO styleFeedDTO);

    StyleFeedDTO updateStyleFeed(Long feedId, StyleFeedDTO styleFeedDTO);

    void deleteStyleFeed(final long feedId);


    List<FeedBookmarkDTO> getAllFeedBookmarks();

    FeedBookmarkDTO createFeedBookmark(FeedBookmarkDTO feedBookmarkDTO);
}
