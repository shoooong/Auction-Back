package com.example.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
public class AdminProductRespDto {
    //    private String modelNum;
//    private String productSize;
//    private List<AdminProductDetailDto> adminProductDetailDtos;
//    private List<BuyinBiddingDto> buyingBiddingDtoList;
//    private List<SalesBiddingDto> salesBiddingDtoList;a
    private AdminProductDetailDto adminProductDetailDto;
    private List<BuyinBiddingDto> buyingBiddingDtoList;
    private List<SalesBiddingDto> salesBiddingDtoList;


    public AdminProductRespDto(AdminProductDetailDto adminProductDetailDto, List<BuyinBiddingDto> buyingBiddingDtoList, List<SalesBiddingDto> salesBiddingDtoList) {
        this.adminProductDetailDto = adminProductDetailDto;
        this.buyingBiddingDtoList = buyingBiddingDtoList;
        this.salesBiddingDtoList = salesBiddingDtoList;
    }
}
