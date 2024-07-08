package com.example.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductRespDto {

    private AdminProductDetailDto adminProductDetailDto;
    private List<BuyinBiddingDto> buyingBiddingDtoList;
    private List<SalesBiddingDto> salesBiddingDtoList;

}
