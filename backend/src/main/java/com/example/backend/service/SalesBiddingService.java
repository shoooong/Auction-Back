package com.example.backend.service;

import static com.example.backend.entity.enumData.SalesStatus.INSPECTION;

import com.example.backend.dto.mypage.saleHistory.SaleDetailsDto;
import com.example.backend.dto.mypage.saleHistory.SaleHistoryDto;
import com.example.backend.dto.mypage.saleHistory.SalesStatusCountDto;
import com.example.backend.dto.orders.BiddingRequestDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.SalesStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesBiddingService {

    private final SalesBiddingRepository salesBiddingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * 판매 입찰 등록
     */
    public void registerSalesBidding(UserDTO userDTO, BiddingRequestDto salesInfo) {
        Product product = productRepository.findById(salesInfo.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not valid"));

        Users user = userRepository.findById(userDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not valid"));

        SalesBidding salesBidding = SalesBidding.builder()
            .salesBiddingPrice(salesInfo.getPrice())
            .product(product)
            .user(user)
            .salesBiddingTime(LocalDateTime
                .now().plusDays(salesInfo.getExp()))
            .salesStatus(INSPECTION)
            .build();

        salesBiddingRepository.save(salesBidding);
    }


    /**
     * 판매 내역
     * 전체, 진행 중, 종료 건수
     * 퍈매 내역 (상품사진, 상품명, 상품사이즈, 판매단가, 판매상태)
     * 판매 입찰 시간 기준 최신순 정렬
     */
    public SaleHistoryDto getSaleHistory(Long userId) {
        Long allCount = salesBiddingRepository.countAllByUserId(userId);
        List<SalesStatusCountDto> SalesStatusCountDto = salesBiddingRepository.countSalesStatus(userId);
        List<SaleDetailsDto> saleDetailsDTO = salesBiddingRepository.findSaleDetailsByUserId(userId);

        return SaleHistoryDto.builder()
                .allCount(allCount)
                .salesStatusCounts(SalesStatusCountDto)
                .saleDetails(saleDetailsDTO)
                .build();
    }

    public SaleHistoryDto getRecentSaleHistory(Long userId) {
        Long allCount = salesBiddingRepository.countAllByUserId(userId);
        List<SalesStatusCountDto> SalesStatusCountDto = salesBiddingRepository.countSalesStatus(userId);
        List<SaleDetailsDto> saleDetailsDTO = salesBiddingRepository.findRecentSaleDetailsByUserId(userId, PageRequest.of(0, 3));

        return SaleHistoryDto.builder()
                .allCount(allCount)
                .salesStatusCounts(SalesStatusCountDto)
                .saleDetails(saleDetailsDTO)
                .build();
    }

    @Transactional
    public void cancelSalesBidding(Long userId, Long salesBiddingId) {
        SalesBidding salesBidding = salesBiddingRepository.findBySalesBiddingIdAndUserUserId(salesBiddingId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 판매 입찰 내역입니다."));

        salesBidding.changeSalesStatus(SalesStatus.CANCEL);
        salesBiddingRepository.save(salesBidding);
    }
}
