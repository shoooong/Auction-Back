package com.example.backend.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBookmarkDTO {
    private Long userId;
    private Long feedId;

    public FeedBookmarkDTO(Long styleSavedId, Long userId, Long feedId) {
    }
}
