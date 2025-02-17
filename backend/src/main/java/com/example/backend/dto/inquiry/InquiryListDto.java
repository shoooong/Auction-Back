package com.example.backend.dto.inquiry;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InquiryListDto {

    private Long inquiryId;
    private String inquiryTitle;
    private String inquiryContent;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
    private Long userId;
}
