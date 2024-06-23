package com.example.backend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private Long bidId;
    private String bidKind;
    private int bidPrice;
    private LocalDateTime bidStartDate;
    private LocalDateTime bidModifyDate;
    private String bidStatus;
    private LocalDateTime bidEndDate;
}
