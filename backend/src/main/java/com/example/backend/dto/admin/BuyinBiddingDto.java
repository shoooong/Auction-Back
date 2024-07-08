package com.example.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyinBiddingDto {

    private Long buyingBiddingId;
    private Long buyingPrice;
    private Long buyingBiddingPrice;
    private LocalDateTime buyingBiddingTime;
    private AdminUserDto buyer;

}
