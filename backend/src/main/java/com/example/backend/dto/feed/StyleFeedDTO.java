package com.example.backend.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StyleFeedDTO {
    private Long feedId;
    private String feedTitle;
    private String feedPhoto;
    private int likeCount;
    private Long userId;
}
