package com.example.backend.service;

import com.example.backend.dto.feed.StyleFeedDTO;
import com.example.backend.entity.StyleFeed;
import com.example.backend.entity.User;
import com.example.backend.repository.StyleFeed.StyleFeedRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class
StyleFeedServiceImpl implements StyleFeedService {

    @Autowired
    private StyleFeedRepository styleFeedRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<StyleFeedDTO> getAllStyleFeeds() {
        List<StyleFeed> styleFeeds = styleFeedRepository.findAll();
        log.info("Found {} StyleFeeds", styleFeeds.size());

        return styleFeeds.stream()
                .map(styleFeed -> new StyleFeedDTO(
                        styleFeed.getFeedId(),
                        styleFeed.getFeedTitle(),
                        styleFeed.getFeedPhoto(),
                        styleFeed.getLikeCount(),
                        styleFeed.getUser().getUserId() // Assuming User entity has getUserId() method
                ))
                .collect(Collectors.toList());
    }

    @Override
    public StyleFeed createStyleFeed(StyleFeedDTO styleFeedDTO) {
        User user = userRepository.findById(styleFeedDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StyleFeed styleFeed = new StyleFeed();
        styleFeed.setFeedTitle(styleFeedDTO.getFeedTitle());
        styleFeed.setFeedPhoto(styleFeedDTO.getFeedPhoto());
        styleFeed.setLikeCount(styleFeedDTO.getLikeCount());
        styleFeed.setUser(user);

        StyleFeed savedStyleFeed = styleFeedRepository.save(styleFeed);
        return savedStyleFeed;
    }
}
