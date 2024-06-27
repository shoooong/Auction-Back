package com.example.backend.controller.feed;

import com.example.backend.dto.feed.FeedBookmarkDTO;
import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.FeedBookmark;
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

    // 최신순으로 피드 조회
    @GetMapping("/feedList")
    public List<StyleFeedDTO> getAllStyleFeedList() {
        List<StyleFeedDTO> styleFeeds = styleFeedService.getAllStyleFeedList();
        log.info("성공: {} 개의 피드 가져옴", styleFeeds);
        return styleFeeds;
    }

    // 좋아요 순으로 피드 조회
    @GetMapping("/feedRanking")
    public List<StyleFeedDTO> getAllStyleFeedRanking() {
        List<StyleFeedDTO> styleFeeds = styleFeedService.getAllStyleFeedRanking();
        log.info("성공: {} 개의 피드 가져옴", styleFeeds);
        return styleFeeds;
    }

    // 피드 상세 조회
    @GetMapping("/{feedId}")
    public StyleFeedDTO getStyleFeedById() {
        StyleFeedDTO styleFeedDTO = styleFeedService.getStyleFeedById(18L);
        log.info("피드 상세 조회: {}", styleFeedDTO);
        return styleFeedDTO;
    }

    // 피드 등록
    @PostMapping("/feed")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStyleFeed(@RequestBody StyleFeedDTO styleFeedDTO) {
        StyleFeed createdStyleFeed = styleFeedService.createStyleFeed(styleFeedDTO);
        log.info("새로운 피드 생성: {}", createdStyleFeed);
    }

    // 피드 수정
    @PutMapping("/{id}")
    public StyleFeedDTO updateStyleFeed(@PathVariable Long id, @RequestBody StyleFeedDTO styleFeedDTO) {
        return styleFeedService.updateStyleFeed(id, styleFeedDTO);
    }

    // 피드 삭제
    @DeleteMapping("/{id}")
    public void deleteStyleFeed(@PathVariable Long id) {
        styleFeedService.deleteStyleFeed(id);
    }

    // 관심피드 조회
    @GetMapping("/saveFeed")
    public List<FeedBookmarkDTO> getAllFeedBookmarks() {
        List<FeedBookmarkDTO> feedBookmarks = styleFeedService.getAllFeedBookmarks();
        log.info("성공: {} 개의 북마크 가져옴", feedBookmarks.size());
        return feedBookmarks;
    }

    // 관심피드 저장
    @PostMapping("/bookmark")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedBookmarkDTO createFeedBookmark(@RequestBody FeedBookmarkDTO feedBookmarkDTO) {
        FeedBookmarkDTO createdFeedBookmark = styleFeedService.createFeedBookmark(feedBookmarkDTO);
        log.info("새로운 북마크 생성: {}", createdFeedBookmark);
        return createdFeedBookmark;
    }
}
