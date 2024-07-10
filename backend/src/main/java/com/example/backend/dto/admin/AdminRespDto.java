package com.example.backend.dto.admin;

import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.enumData.LuckyProcessStatus;
import com.example.backend.entity.enumData.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminRespDto {

    //다건조회 DTO
    @Setter
    @Getter
    public static class ReqProductsRespDto {
        private List<ProductDto> products = new ArrayList<>();

        public ReqProductsRespDto(List<Product> products) {
            this.products = products.stream().map(ProductDto::new).collect(Collectors.toList());
        }
        @Setter
        @Getter
        public class ProductDto {

            private Long productId;
            private String productName;
            private String productBrand;
            private ProductStatus productStatus;
            public ProductDto(Product product) {
                this.productId = product.getProductId();
                this.productName = product.getProductName();
                this.productBrand = product.getProductBrand();
                this.productStatus = product.getProductStatus();
            }
        }
    }

    @Getter
    @Setter
    public static class ReqProductRespDto{
        private Long productId;
        private String productName;
        private String productBrand;
        private String productImg;
        private String modelNum;
        private Long originalPrice;
        private String productSize;

        public ReqProductRespDto(Product product) {
            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.productBrand = product.getProductBrand();
            this.productImg = product.getProductImg();
            this.modelNum = product.getModelNum();
            this.originalPrice = product.getOriginalPrice();
            this.productSize = product.getProductSize();
        }
    }

    @Getter
    @Setter
    public static class RegProductRespDto{
        private Long productId;
        private String productName;
        private String productBrand;
        private String productImg;
        private String modelNum;
        private Long originalPrice;
        private String productSize;
        private ProductStatus productStatus;
        public RegProductRespDto(Product product) {

            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.productBrand = product.getProductBrand();
            this.productImg = product.getProductImg();
            this.modelNum = product.getModelNum();
            this.originalPrice = product.getOriginalPrice();
            this.productSize = product.getProductSize();
            this.productStatus = product.getProductStatus();
        }
    }

    //판매입찰 상태 검수변경 dto
    @Getter
    @Setter
    public static class ChangeRespDto{
        private Long salesBiddingId;
        private Long productId;
        private int productQuantity;

        public ChangeRespDto(SalesBidding salesBidding, Product product) {
            this.salesBiddingId = salesBidding.getSalesBiddingId();
            this.productId = product.getProductId();
            this.productQuantity = product.getProductQuantity();
        }
    }

    //럭키드로우 상태별로 전체 조회
    @Getter
    @Setter
    public static class LuckyDrawsRespDto{
        private LuckyProcessStatus luckyProcessStatus;
        private List<LuckyDrawDto> luckyDraws = new ArrayList<>();

        public LuckyDrawsRespDto(LuckyProcessStatus luckyProcessStatus,List<LuckyDraw> luckyDraws) {
            this.luckyProcessStatus = luckyProcessStatus;
            this.luckyDraws = luckyDraws.stream().map(LuckyDrawDto::new).collect(Collectors.toList());
        }
        @Setter
        @Getter
        public class LuckyDrawDto {

            private Long luckyId;
            private String luckyName;
            private LuckyProcessStatus luckyProcessStatus;

            public LuckyDrawDto(LuckyDraw luckyDraw) {
                this.luckyId = luckyDraw.getLuckyId();
                this.luckyName = luckyDraw.getLuckyName();
                this.luckyProcessStatus = luckyDraw.getLuckyProcessStatus();
            }
        }

    }

    //상품 전체 대분류 소분류별 조회 응답 Dto
    @Getter
    @Setter
    public static class AdminProductResponseDto {
        private String mainDepartment;
        private String subDepartment;
        private List<AdminProductDto> products;

        public AdminProductResponseDto(String mainDepartment, String subDepartment, List<AdminProductDto> products) {
            this.mainDepartment = mainDepartment;
            this.subDepartment = subDepartment;
            this.products = products;
        }
    }

    //상품 상세 응답 Dto
    @Getter
    @Setter
    public static class AdminProductDetailRespDto {
        private String modelNum;
        private String productSize;
        private List<AdminProductRespDto> detailedProducts;

        public AdminProductDetailRespDto(String modelNum, String productSize, List<AdminProductRespDto> detailedProducts) {
            this.modelNum = modelNum;
            this.productSize = productSize;
            this.detailedProducts = detailedProducts;
        }

        // getter, setter, etc.
    }



}
