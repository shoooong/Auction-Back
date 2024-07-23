package com.example.backend.dto.requestproduct;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestProductListDto {

    private Long productId;
    private String productBrand;
    private String productName;
}
