package com.example.backend.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBookmarkDto {
    private Long userId;
    private Long feedId;

    public FeedBookmarkDto(Long styleSavedId, Long userId, Long feedId) {
    }
}
