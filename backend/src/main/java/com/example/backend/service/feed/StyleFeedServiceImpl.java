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
                        styleFeed.getUser().getUserId(),
                        styleFeed.getUser().getNickname()
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
                        styleFeed.getUser().getUserId(),
                        styleFeed.getUser().getNickname()
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
                styleFeed.getUser().getUserId(),
                styleFeed.getUser().getNickname()
        );
    }

    // 피드 등록
    @Override
    public StyleFeed createStyleFeed(StyleFeedDto styleFeedDTO) {
        Users user = userRepository.findById(styleFeedDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StyleFeed styleFeed = new StyleFeed();
        styleFeed.setFeedTitle(styleFeedDTO.getFeedTitle());
        styleFeed.setFeedImage(styleFeedDTO.getFeedImage()); // This now contains the cloud image path(s)
        styleFeed.setLikeCount(styleFeedDTO.getLikeCount());
        styleFeed.setUser(user);

        return styleFeedRepository.save(styleFeed);
    }

    // 피드 좋아요
    @Override
    public void increaseLikeCount(Long feedId) {
        StyleFeed styleFeed = styleFeedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        styleFeed.setLikeCount(styleFeed.getLikeCount() + 1);
        styleFeedRepository.save(styleFeed);
        log.info("피드 좋아요 수 증가: {}", feedId);
    }

    // 피드 수정
    @Override
    public StyleFeedDto updateStyleFeed(Long feedId, StyleFeedDto styleFeedDTO) {
        StyleFeed styleFeed = styleFeedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        Long userId = styleFeed.getUser().getUserId();
        Long requestUserId = styleFeedDTO.getUserId();

        if (!userId.equals(requestUserId)) {
            throw new RuntimeException("User is not authorized to update this feed");
        }

        if (styleFeedDTO.getFeedTitle() != null) {
            styleFeed.setFeedTitle(styleFeedDTO.getFeedTitle());
        }
        if (styleFeedDTO.getFeedImage() != null) {
            styleFeed.setFeedImage(styleFeedDTO.getFeedImage());
        }
        StyleFeed updatedFeed = styleFeedRepository.save(styleFeed);

        return new StyleFeedDto(
                updatedFeed.getFeedId(),
                updatedFeed.getFeedTitle(),
                updatedFeed.getFeedImage(),
                updatedFeed.getLikeCount(),
                updatedFeed.getCreateDate(),
                updatedFeed.getModifyDate(),
                updatedFeed.getUser().getUserId(),
                updatedFeed.getUser().getNickname()
        );
    }

    // 피드 삭제
    @Override
    public void deleteStyleFeed(final long feedId, Long userId) {

        StyleFeed styleFeed = styleFeedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("StyleFeed not found"));

        Long feedUserId = styleFeed.getUser().getUserId();

        if (!feedUserId.equals(userId)) {
            throw new RuntimeException("User is not authorized to delete this feed");
        }

        final List<FeedBookmark> feedBookmarks = feedBookmarkRepository.findByStyleFeed_FeedId(feedId);
        feedBookmarkRepository.deleteAll(feedBookmarks);
        styleFeedRepository.deleteById(feedId);
    }

    // 관심피드 조회
    @Override
    public List<FeedBookmarkDto> getUserFeedBookmarks(Long userId) {
        List<FeedBookmark> feedBookmarks = feedBookmarkRepository.findByUser_UserId(userId);
        log.info("Found {} FeedBookmarks for user {}", feedBookmarks.size(), userId);

        return feedBookmarks.stream()
                .map(feedBookmark -> {
                    FeedBookmarkDto dto = new FeedBookmarkDto();
                    dto.setUserId(feedBookmark.getUser().getUserId());
                    dto.setFeedId(feedBookmark.getStyleFeed().getFeedId());
                    dto.setFeedImage(feedBookmark.getStyleFeed().getFeedImage());
                    dto.setFeedTitle(feedBookmark.getStyleFeed().getFeedTitle());
                    dto.setNickName(feedBookmark.getUser().getNickname());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 관심피드 저장
    @Override
    public FeedBookmarkDto createFeedBookmark(FeedBookmarkDto feedBookmarkDTO) {
        Long userId = feedBookmarkDTO.getUserId();
        Long feedId = feedBookmarkDTO.getFeedId();

        if (feedBookmarkRepository.existsByUser_UserIdAndStyleFeed_FeedId(userId, feedId)) {
            throw new RuntimeException("이미 해당 피드를 저장하였습니다.");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        StyleFeed styleFeed = styleFeedRepository.findById(feedId)
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
    public void deleteFeedBookmark(final long styleSavedId, Long userId) {
        // Check if the feed bookmark belongs to the logged-in user
        FeedBookmark feedBookmark = feedBookmarkRepository.findById(styleSavedId)
                .orElseThrow(() -> new RuntimeException("Feed bookmark not found"));

        if (!feedBookmark.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: Cannot delete feed bookmark");
        }
        feedBookmarkRepository.deleteById(styleSavedId);
    }
}
