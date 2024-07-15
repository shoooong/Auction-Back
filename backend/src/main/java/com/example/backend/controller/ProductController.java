package com.example.backend.controller;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.*;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.Product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//userId를 못가져오면 null값이라는거니까 로그인을 안한상태
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 상품(카테고리 소분류) 불러오기
    @GetMapping("/{subDepartment}")
    public List<ProductResponseDto> products(@PathVariable String subDepartment) {

        log.info("subDepartment : " + subDepartment);
        List<ProductResponseDto> products = productService.selectCategoryValue(subDepartment);
        log.info("상품 정보 : {}", products);
        return products;
    }

    // 해당 상품(상세) 기본 정보 가져오기
    @GetMapping("/details/{modelNum}")
    public ProductDetailDto productDetailSelect(@PathVariable String modelNum) {

        // (상품의 기본 정보) && (해당 상품의 구매(최저) / 판매(최고)가 조회) && (해당 상품에 대한 최근 체결 정보) && 상품 체결 / 구매 / 판매 내역 리스트
        ProductDetailDto basicInformationDto = productService.productDetailInfo(modelNum);
        log.info("basicInformationDto : " + basicInformationDto);

        return basicInformationDto;
    }

    @GetMapping("/details/{modelNum}/bid")
    public ResponseEntity<?> buyingBidSelect(
            @PathVariable String modelNum,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String type,
            @AuthenticationPrincipal UserDTO userDTO) {
        if (userDTO == null) {
            log.info("로그인이 되어있지 않습니다.");
        }
        Long userId = userDTO.getUserId();
        BuyingBidRequestDto bidRequestDto = BuyingBidRequestDto.builder()
                .modelNum(modelNum)
                .productSize(size)
                .type(type)
                .userId(userId)
                .build();
        BuyingBidResponseDto buyingBidResponseDto = productService.selectBuyingBid(bidRequestDto);

        return new ResponseEntity<>(buyingBidResponseDto, HttpStatus.OK);

    }

    @PostMapping("/details/{modelNum}/bid")
    public ResponseEntity<?> bidApplication(
            @PathVariable String modelNum,
            @RequestBody BidRequestDto bidRequestDto,
            @AuthenticationPrincipal UserDTO userDTO) {
        if (userDTO == null) {
            log.info("로그인이 되어있지 않습니다.");
        }
        Long userId = userDTO.getUserId();
        bidRequestDto.setUserId(userId);
        bidRequestDto.setModelNum(modelNum);
        productService.saveTemporaryBid(bidRequestDto);
        return ResponseEntity.ok("값이 정상적으로 저장되었습니다.");
    }

//    @PutMapping("/details/{modelNum}/bid/{buyingBiddingId}")
//    public ResponseEntity<?> updateBid(
//            @PathVariable String modelNum,
//            @PathVariable Long buyingBiddingId,
//            @RequestBody
//    )

    // 리뷰 작성
    @PostMapping("/details/{modelNum}/review")
    public ResponseEntity<?> productDetailReview(@PathVariable String modelNum, @RequestBody PhotoRequestDto photoRequestDto, @AuthenticationPrincipal UserDTO userDTO) {
        if (userDTO == null) {
            log.info("로그인을 해야한다~");
        }
        Long userId = userDTO.getUserId();
        photoRequestDto.setUserId(userId);
        photoRequestDto.setModelNum(modelNum);

        productService.addPhotoReview(photoRequestDto);
        return ResponseEntity.ok("리뷰가 성공적으로 작성되었습니다.");
    }


    @PutMapping("/details/{modelNum}/review/{reviewId}")
    public ResponseEntity<?> updatePhotoReview(
            @PathVariable String modelNum,
            @PathVariable Long reviewId,
            @RequestBody PhotoRequestDto photoRequestDto,
            @AuthenticationPrincipal UserDTO userDTO) {

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = userDTO.getUserId();
        photoRequestDto.setUserId(userId);
        photoRequestDto.setModelNum(modelNum);
        photoRequestDto.setReviewId(reviewId);

        productService.updatePhotoReview(photoRequestDto);
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/details/{modelNum}/review/{reviewId}")
    public ResponseEntity<?> deletePhotoReview(@PathVariable String modelNum, @PathVariable Long reviewId, @AuthenticationPrincipal UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = userDTO.getUserId();

        productService.deletePhotoReview(reviewId, userId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

//    @GetMapping("/products/average-prices/{modelNum}")
//    public AveragePriceResponseDto getAveragePrices(@PathVariable String modelNum) {
//        return productService.getAveragePrices(modelNum);
//    }
}