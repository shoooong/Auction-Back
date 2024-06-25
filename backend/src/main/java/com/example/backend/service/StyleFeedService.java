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

    List<FeedBookmarkDTO> getAllFeedBookmarks();

    FeedBookmarkDTO createFeedBookmark(FeedBookmarkDTO feedBookmarkDTO);
}
