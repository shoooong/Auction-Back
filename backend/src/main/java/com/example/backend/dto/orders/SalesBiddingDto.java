package com.example.backend.dto.orders;

import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.SalesStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalesBiddingDto {
    private Long salesBiddingId;

    private OrderProductDto product;

    private BigDecimal salesBiddingPrice;

    private int salesQuantity;

    private LocalDateTime salesBiddingTime;

    private SalesStatus salesStatus;

}
