package com.example.backend.dto.requestProduct;

import com.example.backend.entity.Category;
import com.example.backend.entity.RequestProduct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class RequestProductRespDto {


    // 요청상품 전체 조회
    @Setter
    @Getter
    public static class ReqProductListRespDto {
        private List<RequestProductDto> reqProducts = new ArrayList<>();

        //요청상품 리스트 담음 DTO
        public ReqProductListRespDto(List<RequestProduct> requestProducts) {
            this.reqProducts = requestProducts.stream()
                    .map(RequestProductDto::new)
                    .collect(Collectors.toList());
        }
        @Getter
        @Setter
        @ToString
        public static class RequestProductDto {
            private Long requestId;
            private String requestTitle;
            private String requestProductName;
//            private LocalDate requestDate;
            private boolean approval;

            public RequestProductDto(RequestProduct requestProduct) {
                this.requestId = requestProduct.getRequestId();
                this.requestTitle = requestProduct.getRequestTitle();
                this.requestProductName = requestProduct.getRequestProductName();
//                this.requestDate = requestProduct.getRequestDate();
                this.approval = requestProduct.isApproval();
            }
        }
    }

    //요청 상품 단건 조회 dto
    @Getter
    @Setter
    public static class ReqProductRespDto {

        private Long requestId;
        private String requestProductName;
        private String brand;
        private BigDecimal openPrice;
        private BigDecimal requestPrice;
        private String requestProductSize;
        private String requestProductColor;
        private String requestProductImage;


        public ReqProductRespDto(RequestProduct requestProduct) {

            this.requestId = requestProduct.getRequestId();
            this.requestProductName = requestProduct.getRequestProductName();
            this.brand = requestProduct.getBrand();
            this.openPrice = requestProduct.getOpenPrice();
            this.requestProductSize = requestProduct.getRequestProductSize();
            this.requestProductColor = requestProduct.getRequestProductColor();
            this.requestProductImage = requestProduct.getRequestProductImage();
        }

    }
    @Setter
    @Getter
    public static class ReqProductSaveRespDto{

        private Long requestId;

        private String requestTitle;

        private String requestProductImage;

        private String brand;

        private BigDecimal openPrice;

        private String requestProductName;

        private String requestProductSize;

        private String requestProductColor;

        private String modelNum;

        private Category category;

        public ReqProductSaveRespDto(RequestProduct requestProduct) {
            this.requestId = requestProduct.getRequestId();
            this.requestTitle = requestProduct.getRequestTitle();
            this.requestProductImage = requestProduct.getRequestProductImage();
            this.brand = requestProduct.getBrand();
            this.openPrice = requestProduct.getOpenPrice();
            this.requestProductName = requestProduct.getRequestProductName();
            this.requestProductSize = requestProduct.getRequestProductSize();
            this.modelNum = requestProduct.getModelNum();
            this.requestProductColor = requestProduct.getRequestProductColor();
            this.category = requestProduct.getCategory();
        }
    }

}
