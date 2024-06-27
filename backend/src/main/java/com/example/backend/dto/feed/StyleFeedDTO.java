package com.example.backend.dto.feed;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StyleFeedDTO {
    private Long feedId;
    private String feedTitle;
    private String feedPhoto;
    private int likeCount;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Long userId;

    public StyleFeedDTO(Long feedId, String feedTitle, String feedPhoto, int likeCount, Long userId) {
    }
}
