package com.example.backend.service;

import com.example.backend.dto.feed.FeedBookmarkDTO;
import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.FeedBookmark;
import com.example.backend.entity.StyleFeed;
import com.example.backend.entity.User;
import com.example.backend.repository.FeedBookmark.FeedBookmarkRepository;
import com.example.backend.repository.StyleFeed.StyleFeedRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public List<StyleFeedDTO> getAllStyleFeedList() {
        List<StyleFeed> styleFeeds = styleFeedRepository.findAllByOrderByCreatedAtDesc();
        log.info("Found {} StyleFeeds", styleFeeds.size());

        return styleFeeds.stream()
                .map(styleFeed -> new StyleFeedDTO(
                        styleFeed.getFeedId(),
                        styleFeed.getFeedTitle(),
                        styleFeed.getFeedPhoto(),
                        styleFeed.getLikeCount(),
                        styleFeed.getCreatedAt(),
                        styleFeed.getUser() != null ? styleFeed.getUser().getUserId() : null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<StyleFeedDTO> getAllStyleFeedRanking() {
        List<StyleFeed> styleFeeds = styleFeedRepository.findAllByOrderByLikeCountDesc();
        log.info("Found {} StyleFeeds", styleFeeds.size());

        return styleFeeds.stream()
                .map(styleFeed -> new StyleFeedDTO(
                        styleFeed.getFeedId(),
                        styleFeed.getFeedTitle(),
                        styleFeed.getFeedPhoto(),
                        styleFeed.getLikeCount(),
                        styleFeed.getCreatedAt(),
                        styleFeed.getUser() != null ? styleFeed.getUser().getUserId() : null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public StyleFeedDTO getStyleFeedById(Long feedId) {
        StyleFeed styleFeed = (StyleFeed) styleFeedRepository.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        return new StyleFeedDTO(
                styleFeed.getFeedId(),
                styleFeed.getFeedTitle(),
                styleFeed.getFeedPhoto(),
                styleFeed.getLikeCount(),
                styleFeed.getCreatedAt(),
                styleFeed.getUser().getUserId()
        );
    }

    public StyleFeed createStyleFeed(StyleFeedDTO styleFeedDTO) {
        User user = userRepository.findById(styleFeedDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StyleFeed styleFeed = new StyleFeed();
        styleFeed.setFeedTitle(styleFeedDTO.getFeedTitle());
        styleFeed.setFeedPhoto(styleFeedDTO.getFeedPhoto());
        styleFeed.setLikeCount(styleFeedDTO.getLikeCount());
        styleFeed.setUser(user);
        styleFeed.setCreatedAt(LocalDateTime.now()); // 현재 시각 설정

        StyleFeed savedStyleFeed = styleFeedRepository.save(styleFeed);
        return savedStyleFeed;
    }

    @Override
    public List<FeedBookmarkDTO> getAllFeedBookmarks() {
        List<FeedBookmark> feedBookmarks = feedBookmarkRepository.findAll();
        log.info("Found {} FeedBookmarks", feedBookmarks.size());

        return feedBookmarks.stream()
                .map(feedBookmark -> new FeedBookmarkDTO(
                        feedBookmark.getUser().getUserId(),
                        feedBookmark.getStyleFeed().getFeedId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public FeedBookmarkDTO createFeedBookmark(FeedBookmarkDTO feedBookmarkDTO) {
        User user = userRepository.findById(feedBookmarkDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        StyleFeed styleFeed = styleFeedRepository.findById(feedBookmarkDTO.getFeedId())
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        FeedBookmark feedBookmark = FeedBookmark.builder()
                .user(user)
                .styleFeed(styleFeed)
                .build();

        FeedBookmark savedFeedBookmark = feedBookmarkRepository.save(feedBookmark);
        log.info("새로운 북마크 생성: {}", savedFeedBookmark);

        return new FeedBookmarkDTO(
                savedFeedBookmark.getStyleSavedId(),
                savedFeedBookmark.getUser().getUserId(),
                savedFeedBookmark.getStyleFeed().getFeedId()
        );
    }
}
