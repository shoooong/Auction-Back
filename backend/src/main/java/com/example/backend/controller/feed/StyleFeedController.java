package com.example.backend.controller.feed;

import com.example.backend.dto.feed.FeedBookmarkDto;
import com.example.backend.dto.feed.StyleFeedDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.feed.StyleFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
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
    @GetMapping("/styleFeed/{feedId}")
    public StyleFeedDto getStyleFeedById(@PathVariable Long feedId) {
        StyleFeedDto styleFeedDto = styleFeedService.getStyleFeedById(feedId);
        log.info("피드 상세 조회: {}", styleFeedDto);
        return styleFeedDto;
    }

    // 피드 등록
    @PostMapping("/feedRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStyleFeed(@RequestBody StyleFeedDto styleFeedDto,
                                @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        styleFeedDto.setUserId(userId);

        styleFeedService.createStyleFeed(styleFeedDto);
        log.info("새로운 피드 생성: {}", styleFeedDto);
    }

    // 피드 수정
    @PutMapping("modifyFeed/{feedId}")
    public StyleFeedDto updateStyleFeed(@PathVariable Long feedId,
                                        @RequestBody StyleFeedDto styleFeedDto,
                                        @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        styleFeedDto.setUserId(userId);

        return styleFeedService.updateStyleFeed(feedId, styleFeedDto);
    }


    // 피드 삭제
    @DeleteMapping("deleteFeed/{feedId}")
    public void deleteStyleFeed(@PathVariable Long feedId,
                                @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        styleFeedService.deleteStyleFeed(feedId, userId);
    }


    // 관심피드 조회
    @GetMapping("/feedBookmark")
    public List<FeedBookmarkDto> getUserFeedBookmarks(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        List<FeedBookmarkDto> feedBookmarks = styleFeedService.getUserFeedBookmarks(userId);
        log.info("성공: {} 개의 북마크 가져옴", feedBookmarks.size());
        return feedBookmarks;
    }

    // 관심피드 저장
    @PostMapping("/saveFeedBookmark")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedBookmarkDto createFeedBookmark(@RequestBody FeedBookmarkDto feedBookmarkDTO,
                                              @AuthenticationPrincipal UserDTO userDTO) throws IOException {
        Long userId = userDTO.getUserId();
        feedBookmarkDTO.setUserId(userId);

        FeedBookmarkDto createdFeedBookmark = styleFeedService.createFeedBookmark(feedBookmarkDTO);
        log.info("새로운 북마크 생성: {}", createdFeedBookmark);
        return createdFeedBookmark;
    }
    // 관심피드 삭제
    @DeleteMapping("deleteFeedBookmark/{styleSavedId}")
    public void deleteFeedBookmark(@PathVariable Long styleSavedId, @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        styleFeedService.deleteFeedBookmark(styleSavedId, userId);
    }
}
