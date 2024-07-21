package com.example.backend.service;

import static com.example.backend.entity.enumData.SalesStatus.COMPLETE;

import com.example.backend.dto.orders.BuyingBiddingDto;
import com.example.backend.dto.orders.OrderProductDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Orders;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.OrderStatus;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Orders.OrdersRepository;
import com.example.backend.repository.User.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyingBiddingService {

    private final BuyingBiddingRepository buyingBiddingRepository;
    private final OrdersRepository ordersRepository;
    private final SalesBiddingRepository salesBiddingRepository;
    private final UserRepository userRepository;

    /**
     * 구매입찰 Id로 해당 입찰 데이터 가져오기
     */

    public BuyingBiddingDto getBuyingBiddingDto(Long buyingBiddingId) {
        BuyingBidding buyingBidding = buyingBiddingRepository.findById(buyingBiddingId)
            .orElseThrow(() -> new RuntimeException("BuyingBidding not valid"));

        return BuyingBiddingDto.builder()
            .buyingBiddingId(buyingBidding.getBuyingBiddingId())
            .product(
                OrderProductDto.builder()
                    .productId(buyingBidding.getProduct().getProductId())
                    .productName(buyingBidding.getProduct().getProductName())
                    .productImg(buyingBidding.getProduct().getProductImg())
                    .productBrand(buyingBidding.getProduct().getProductBrand())
                    .modelNum(buyingBidding.getProduct().getModelNum())
                    .productSize(buyingBidding.getProduct().getProductSize())
                    .build())
            .buyingQuantity(buyingBidding.getBuyingQuantity())
            .buyingBiddingPrice(buyingBidding.getBuyingBiddingPrice())
            .buyingBiddingTime(buyingBidding.getBuyingBiddingTime())
            .biddingStatus(buyingBidding.getBiddingStatus())
            .build();
    }

    /**
     * 즉시구매시 판매입찰 데이터로 구매입찰 생성(COMPLETE)
     */

    @Transactional
    public void applyBuyNow(Long salesBiddingId, UserDTO userDTO) {
        SalesBidding salesBidding = salesBiddingRepository.findById(salesBiddingId) // 판매입찰 데이터 가져오기
            .orElseThrow(() -> new RuntimeException("SalesBidding not valid"));
        Users user = userRepository.findById(userDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not valid"));

        BuyingBidding buyingBidding = BuyingBidding.builder() // 즉시구매 데이터 생성
            .user(user)
            .product(salesBidding.getProduct())
            .buyingBiddingPrice(salesBidding.getSalesBiddingPrice())
            .buyingQuantity(salesBidding.getSalesQuantity())
            .buyingBiddingTime(LocalDateTime.now())
            .biddingStatus(BiddingStatus.COMPLETE)
            .build();

        salesBidding.changeSalesStatus(COMPLETE); // 해당 판매입찰건 완료

        buyingBiddingRepository.save(buyingBidding);
    }
    @Transactional
    public void cancelBuyingBidding(Long userId, Long buyingBiddingId) {
        BuyingBidding buyingBidding = buyingBiddingRepository.findByBuyingBiddingIdAndUserUserId(buyingBiddingId, userId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매 입찰 내역입니다."));

        buyingBidding.changeBiddingStatus(BiddingStatus.CANCEL);
        buyingBiddingRepository.save(buyingBidding);

        Optional<Orders> optOrder = ordersRepository.findByBuyingBiddingBuyingBiddingId(buyingBiddingId);
        optOrder.ifPresent(order -> {
            order.changeOrderStatus(OrderStatus.CANCEL);
            ordersRepository.save(order);
        });
    }
}
