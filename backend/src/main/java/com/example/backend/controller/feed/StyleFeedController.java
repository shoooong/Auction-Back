package com.example.backend.controller.feed;

import com.example.backend.dto.feed.FeedBookmarkDto;
import com.example.backend.dto.feed.StyleFeedDto;
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
    public List<StyleFeedDto> getAllStyleFeedList() {
        List<StyleFeedDto> styleFeeds = styleFeedService.getAllStyleFeedList();
        log.info("성공: {} 개의 피드 가져옴", styleFeeds);
        return styleFeeds;
    }

    // 좋아요 순으로 피드 조회
    @GetMapping("/feedRanking")
    public List<StyleFeedDto> getAllStyleFeedRanking() {
        List<StyleFeedDto> styleFeeds = styleFeedService.getAllStyleFeedRanking();
        log.info("성공: {} 개의 피드 가져옴", styleFeeds);
        return styleFeeds;
    }

    // 피드 상세 조회
    @GetMapping("/{feedId}")
    public StyleFeedDto getStyleFeedById() {
        StyleFeedDto styleFeedDTO = styleFeedService.getStyleFeedById(18L);
        log.info("피드 상세 조회: {}", styleFeedDTO);
        return styleFeedDTO;
    }

    // 피드 등록
    @PostMapping("/feed")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStyleFeed(@RequestBody StyleFeedDto styleFeedDTO) {
        StyleFeed createdStyleFeed = styleFeedService.createStyleFeed(styleFeedDTO);
        log.info("새로운 피드 생성: {}", createdStyleFeed);
    }

    // 피드 수정
    @PutMapping("modify/{feedId}")
    public StyleFeedDto updateStyleFeed(@PathVariable Long feedId, @RequestBody StyleFeedDto styleFeedDTO) {
        return styleFeedService.updateStyleFeed(feedId, styleFeedDTO);
    }

    // 피드 삭제
    @DeleteMapping("style/{feedId}")
    public void deleteStyleFeed(@PathVariable Long feedId) {
        styleFeedService.deleteStyleFeed(feedId);
    }

    // 관심피드 조회
    @GetMapping("/saveFeed")
    public List<FeedBookmarkDto> getAllFeedBookmarks() {
        List<FeedBookmarkDto> feedBookmarks = styleFeedService.getAllFeedBookmarks();
        log.info("성공: {} 개의 북마크 가져옴", feedBookmarks.size());
        return feedBookmarks;
    }

    // 관심피드 저장
    @PostMapping("/bookmark")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedBookmarkDto createFeedBookmark(@RequestBody FeedBookmarkDto feedBookmarkDTO) {
        FeedBookmarkDto createdFeedBookmark = styleFeedService.createFeedBookmark(feedBookmarkDTO);
        log.info("새로운 북마크 생성: {}", createdFeedBookmark);
        return createdFeedBookmark;
    }

    // 관심피드 삭제
    @DeleteMapping("saveStyle/{styleSavedId}")
    public void deleteFeedBookmark(@PathVariable Long styleSavedId) {
        styleFeedService.deleteFeedBookmark(styleSavedId);
    }
}
