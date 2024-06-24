package com.example.backend.controller.feed;

import com.example.backend.entity.StyleFeed;
import com.example.backend.service.StyleFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/feed")
@RestController  // @Controller 대신 @RestController를 사용하여 JSON 응답을 반환
public class StyleFeedController {

    @Autowired
    private StyleFeedService styleFeedService;

    @GetMapping("/feedList")
    public List<StyleFeed> listStyleFeeds() {
        return styleFeedService.getAllStyleFeeds();
    }
}
