package com.example.backend.dto.admin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//보여주기식,
public class AcceptSaleRespDto{
    private SalesBiddingDto salesBiddingDto;
    private AdminProductDto adminProductDto;
    private AdminUserDto adminUserDto;

}
