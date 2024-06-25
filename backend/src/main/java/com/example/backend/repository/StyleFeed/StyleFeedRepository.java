package com.example.backend.repository.StyleFeed;

import com.example.backend.entity.StyleFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StyleFeedRepository extends JpaRepository<StyleFeed, Long> {
    List<StyleFeed> findAllByOrderByLikeCountDesc();

    List<StyleFeed> findAllByOrderByCreatedAtDesc();

    Optional<Object> findByFeedId(Long feedId);
}
