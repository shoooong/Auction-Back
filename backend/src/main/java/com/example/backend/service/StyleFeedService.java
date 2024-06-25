package com.example.backend.service;

import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.StyleFeed;

import java.util.List;

public interface StyleFeedService {

    List<StyleFeedDTO> getAllStyleFeeds();

    StyleFeed createStyleFeed(StyleFeedDTO styleFeedDTO);
}
