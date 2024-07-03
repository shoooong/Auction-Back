package com.example.backend.dto.feed;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBookmarkDto {
    private Long userId;
    private Long feedId;
    private String feedImage;

    public FeedBookmarkDto(Long feedBookmarkId, Long userId, Long feedId) {
    }
}
