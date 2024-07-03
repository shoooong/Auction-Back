package com.example.backend.dto.inquiry;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InquiryResponseDto {

    private Long responseId;
    private Long inquiryId;
    private Long userId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String response;

}
