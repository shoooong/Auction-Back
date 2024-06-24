package com.example.backend.service;

import com.example.backend.entity.StyleFeed;
import com.example.backend.repository.StyleFeed.StyleFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StyleFeedService {

    @Autowired
    private StyleFeedRepository styleFeedRepository;

    public void saveStyleFeed(StyleFeed styleFeed) {
        styleFeedRepository.save(styleFeed);
    }

    public List<StyleFeed> getAllStyleFeeds() {
        return styleFeedRepository.findAll();
    }
}
