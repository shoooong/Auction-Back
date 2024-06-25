package com.example.backend.controller.feed;

import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.StyleFeed;
import com.example.backend.service.StyleFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
public class StyleFeedController {

    @Autowired
    private StyleFeedService styleFeedService;

    @GetMapping("/feedList")
    public List<StyleFeedDTO> getAllStyleFeeds() {
        List<StyleFeedDTO> styleFeeds = styleFeedService.getAllStyleFeeds();
        log.info("성공: {} 개의 피드 가져옴", styleFeeds.size());
        return styleFeeds;
    }

    @PostMapping("/feed")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStyleFeed(@RequestBody StyleFeedDTO styleFeedDTO) {
        StyleFeed createdStyleFeed = styleFeedService.createStyleFeed(styleFeedDTO);
        log.info("새로운 피드 생성: {}", createdStyleFeed);
    }
}
