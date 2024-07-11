package com.example.backend.dto.product.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfoDto {
    private String productSize;
    private Long contractPrice;
    private LocalDate contractDate;
//    List<SalesBidding> salesBiddingList;
}
