package com.example.backend.dto.Notice;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NoticeDto {

    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long userId;
}
