package com.example.backend.repository.StyleFeed;

import com.example.backend.entity.StyleFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StyleFeedRepository extends JpaRepository<StyleFeed, Long> {
    List<StyleFeed> findAllByOrderByLikeCountDesc();
}
