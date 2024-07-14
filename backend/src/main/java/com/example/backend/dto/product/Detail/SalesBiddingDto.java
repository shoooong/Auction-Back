package com.example.backend.dto.product.Detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesBiddingDto {

    private Long productId;
    private String modelNum;
    private String productSize;
    private Long latestPrice;
    private Long previousPrice;
    private Double previousPercentage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime salesBiddingTime;
    private Long salesBiddingPrice;
}
