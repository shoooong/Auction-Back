package com.example.backend.dto.Announcement;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AnnouncementDTO {

    private Long announcementId;
    private String announcementTitle;
    private String announcementContent;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long userId;
}
