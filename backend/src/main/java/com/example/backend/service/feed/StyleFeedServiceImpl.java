package com.example.backend.service.feed;

import com.example.backend.dto.feed.FeedBookmarkDto;
import com.example.backend.dto.feed.StyleFeedDto;
import com.example.backend.entity.FeedBookmark;
import com.example.backend.entity.StyleFeed;
import com.example.backend.entity.Users;
import com.example.backend.repository.FeedBookmark.FeedBookmarkRepository;
import com.example.backend.repository.StyleFeed.StyleFeedRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@Log4j2
public class StyleFeedServiceImpl implements StyleFeedService {

    @Autowired
    private StyleFeedRepository styleFeedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedBookmarkRepository feedBookmarkRepository;

    // 최신 등록순으로 피드 조회
    @Override
    public List<StyleFeedDto> getAllStyleFeedList() {
        List<StyleFeed> styleFeeds = styleFeedRepository.findAllByOrderByCreateDateDesc();
        log.info("Found {} StyleFeeds", styleFeeds.size());

        return styleFeeds.stream()
                .map(styleFeed -> new StyleFeedDto(
                        styleFeed.getFeedId(),
                        styleFeed.getFeedTitle(),
                        styleFeed.getFeedImage(),
                        styleFeed.getLikeCount(),
                        styleFeed.getCreateDate(),
                        styleFeed.getModifyDate(),
                        styleFeed.getUser() != null ? styleFeed.getUser().getUserId() : null
                ))
                .collect(Collectors.toList());
    }

    // 좋아요 순으로 피드 조회
    @Override
    public List<StyleFeedDto> getAllStyleFeedRanking() {
        List<StyleFeed> styleFeeds = styleFeedRepository.findAllByOrderByLikeCountDesc();
        log.info("Found {} StyleFeeds", styleFeeds.size());

        return styleFeeds.stream()
                .map(styleFeed -> new StyleFeedDto(
                        styleFeed.getFeedId(),
                        styleFeed.getFeedTitle(),
                        styleFeed.getFeedImage(),
                        styleFeed.getLikeCount(),
                        styleFeed.getCreateDate(),
                        styleFeed.getModifyDate(),
                        styleFeed.getUser() != null ? styleFeed.getUser().getUserId() : null
                ))
                .collect(Collectors.toList());
    }

    // 피드 상세 조회
    @Override
    public StyleFeedDto getStyleFeedById(Long feedId) {
        StyleFeed styleFeed = styleFeedRepository.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        return new StyleFeedDto(
                styleFeed.getFeedId(),
                styleFeed.getFeedTitle(),
                styleFeed.getFeedImage(),
                styleFeed.getLikeCount(),
                styleFeed.getCreateDate(),
                styleFeed.getModifyDate(),
                styleFeed.getUser().getUserId()
        );
    }

    // 피드 등록
    public StyleFeed createStyleFeed(StyleFeedDto styleFeedDTO) {
        Users user = userRepository.findById(styleFeedDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StyleFeed styleFeed = new StyleFeed();
        styleFeed.setFeedTitle(styleFeedDTO.getFeedTitle());
        styleFeed.setFeedImage(styleFeedDTO.getFeedImage());
        styleFeed.setLikeCount(styleFeedDTO.getLikeCount());
        styleFeed.setUser(user);

        StyleFeed savedStyleFeed = styleFeedRepository.save(styleFeed);
        return savedStyleFeed;
    }

    // 피드 수정
    @Override
    public StyleFeedDto updateStyleFeed(Long feedId, StyleFeedDto styleFeedDTO) {
        StyleFeed styleFeed = styleFeedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        if (styleFeedDTO.getFeedTitle() != null) {
            styleFeed.setFeedTitle(styleFeedDTO.getFeedTitle());
        }
        if (styleFeedDTO.getFeedImage() != null) {
            styleFeed.setFeedImage(styleFeedDTO.getFeedImage());
        }
        if (styleFeedDTO.getLikeCount() != 0) {
            styleFeed.setLikeCount(styleFeedDTO.getLikeCount());
        }

        StyleFeed updatedFeed = styleFeedRepository.save(styleFeed);

        return new StyleFeedDto(
                updatedFeed.getFeedId(),
                updatedFeed.getFeedTitle(),
                updatedFeed.getFeedImage(),
                updatedFeed.getLikeCount(),
                updatedFeed.getCreateDate(),
                updatedFeed.getModifyDate(),
                updatedFeed.getUser().getUserId()
        );
    }

    // 피드 삭제
    @Override
    public void deleteStyleFeed(final long feedId) {
        final List<FeedBookmark> feedBookmarks = feedBookmarkRepository.findByStyleFeed_FeedId(feedId);
        feedBookmarkRepository.deleteAll(feedBookmarks);
        styleFeedRepository.deleteById(feedId);
    }


    // 관심피드 조회
    @Override
    public List<FeedBookmarkDto> getAllFeedBookmarks() {
        List<FeedBookmark> feedBookmarks = feedBookmarkRepository.findAll();
        log.info("Found {} FeedBookmarks", feedBookmarks);

        return feedBookmarks.stream()
                .map(feedBookmark -> {
                    FeedBookmarkDto dto = new FeedBookmarkDto();
                    dto.setUserId(feedBookmark.getUser().getUserId());
                    dto.setFeedId(feedBookmark.getStyleFeed().getFeedId());
                    dto.setFeedImage(feedBookmark.getStyleFeed().getFeedImage());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    // 관심피드 등록
    @Override
//    @Transactional
    public FeedBookmarkDto createFeedBookmark(FeedBookmarkDto feedBookmarkDTO) {
        Users user = userRepository.findById(feedBookmarkDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        StyleFeed styleFeed = styleFeedRepository.findById(feedBookmarkDTO.getFeedId())
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        FeedBookmark feedBookmark = FeedBookmark.builder()
                .user(user)
                .styleFeed(styleFeed)
                .build();

        FeedBookmark savedFeedBookmark = feedBookmarkRepository.save(feedBookmark);
        log.info("새로운 북마크 생성: {}", savedFeedBookmark);

        return new FeedBookmarkDto(
                savedFeedBookmark.getFeedBookmarkId(),
                savedFeedBookmark.getUser().getUserId(),
                savedFeedBookmark.getStyleFeed().getFeedId()
        );
    }

    // 관심피드 삭제
    @Override
    public void deleteFeedBookmark(final long styleSavedId) {
        feedBookmarkRepository.deleteById(styleSavedId);
    }
}
