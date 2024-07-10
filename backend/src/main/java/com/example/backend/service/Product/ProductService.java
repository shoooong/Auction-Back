package com.example.backend.service.Product;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.*;

import java.util.List;

public interface ProductService {

    // 상품 카테고리에 따라 상품 정보 조회 (소분류)
    List<ProductResponseDto> selectCategoryValue(String subDepartment);

    // 상세 상품 기본 정보 조회
    BasicInformationDto basicInformation(String modelNum);

    RecentlyPriceDto selectRecentlyPrice(String modelNum);

    List<ProductsContractListDto> selectSalesContract(String modelNum);

    List<SalesHopeDto> selectSalesHope(String modelNum);

    List<BuyingHopeDto> selectBuyingHope(String modelNum);

    void addPhotoReview(PhotoRequestDto photoRequestDto);
    void updatePhotoReview(PhotoRequestDto photoRequestDto);
    void deletePhotoReview(Long reviewId, Long userId);

    List<PhotoReviewDto> selectPhotoReview(String modelNum);

//    List<ContractInfoDto> selectContractValue(String modelNum);

//    AveragePriceResponseDto getAveragePrices(String modelNum);
}