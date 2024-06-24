package com.example.backend.dto.requestProduct;

import com.example.backend.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class RequestProductReqDto {

    @Getter
    @Setter
    public static class ReqProductSaveReqDto{
        private Long requestId;

        private String requestTitle;

        private String requestProductImage;

        private BigDecimal requestPrice;

        private String brand;

        private BigDecimal openPrice;

        private String requestProductName;

        private String requestProductSize;

        private String requestProductColor;

        private Category category;
    }

}
