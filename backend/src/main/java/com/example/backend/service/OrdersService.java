package com.example.backend.service;

import static com.example.backend.entity.enumData.OrderStatus.WAITING;

import com.example.backend.dto.mypage.buyHistory.BuyHistoryDto;
import com.example.backend.dto.mypage.buyHistory.OrderDetailsDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.SaleOrderDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Coupon;
import com.example.backend.entity.CouponIssue;
import com.example.backend.entity.Orders;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.CouponIssue.CouponIssueRepository;
import com.example.backend.repository.Orders.OrdersRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.coupon.CouponRepository;
import com.example.backend.service.coupon.CouponService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BuyingBiddingRepository buyingBiddingRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponService couponService;
    private final SalesBiddingRepository salesBiddingRepository;


    //구매입찰<->즉시판매
    @Transactional
    public Orders createBuyOrder(UserDTO userDto, BuyOrderDto buyOrderDto) {
        Users user = userRepository.findById(userDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(buyOrderDto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        BuyingBidding buyingBidding = buyingBiddingRepository.findById(
                buyOrderDto.getBuyingBiddingId())
            .orElseThrow(() -> new RuntimeException("BuyingBidding not found"));

        BigDecimal totalAmount = buyingBidding.getBuyingBiddingPrice();
        Coupon coupon = null;

        if (buyOrderDto.getCouponId() != null) {
            coupon = couponRepository.findById(buyOrderDto.getCouponId())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
            CouponIssue userCoupon = couponIssueRepository.findByUsersAndCouponAndUseStatusFalse(
                    user, coupon)
                .orElseThrow(() -> new RuntimeException("Coupon not valid"));
            totalAmount = couponService.applyCoupon(user, coupon, totalAmount)
                .setScale(2, RoundingMode.HALF_UP);

            userCoupon.useCoupon(true);
            userCoupon.useDate();
            couponIssueRepository.save(userCoupon);
        }

        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            totalAmount = BigDecimal.ZERO;
        }

        Orders order = Orders.builder()
            .user(user)
            .product(product)
            .buyingBidding(buyingBidding)
            .coupon(coupon)
            .orderStatus(WAITING)
            .orderPrice(totalAmount)
            .build();

        return ordersRepository.save(order);
    }


    // 판매입찰 <-> 즉시구매
    @Transactional
    public Orders createSaleOrder(UserDTO userDto, SaleOrderDto saleOrderDto) {
        Users user = userRepository.findById(userDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(saleOrderDto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        SalesBidding salesBidding = salesBiddingRepository.findById(
                saleOrderDto.getSalesBiddingId())
            .orElseThrow(() -> new RuntimeException("SalesBidding not found"));

        BigDecimal totalAmount = BigDecimal.valueOf(saleOrderDto.getSalesBiddingId());
        Coupon coupon = null;



        if (saleOrderDto.getCouponId() != null) {
            coupon = couponRepository.findById(saleOrderDto.getCouponId())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
            CouponIssue userCoupon = couponIssueRepository.findByUsersAndCouponAndUseStatusFalse(
                    user, coupon)
                .orElseThrow(() -> new RuntimeException("Coupon not valid"));
            totalAmount = couponService.applyCoupon(user, coupon, totalAmount)
                .setScale(2, RoundingMode.HALF_UP);


            userCoupon.useCoupon(true);
            couponIssueRepository.save(userCoupon);
        }

        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            totalAmount = BigDecimal.ZERO;
        }


        Orders order = Orders.builder()
            .user(user)
            .product(product)
            .salesBidding(salesBidding)
            .coupon(coupon)
            .orderStatus(WAITING)
            .orderPrice(totalAmount)
            .build();

        return ordersRepository.save(order);
    }


    /**
     * 구매 내역 전체, 진행 중, 종료 건수 구매 내역 (상품사진, 상품명, 상품사이즈, 결제금액, 주문상태) 주문날짜 기준 최신순 정렬
     */
    public BuyHistoryDto getBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = buyingBiddingRepository.countCompleteByUserId(userId);

        List<OrderDetailsDto> orderDetailsDto = ordersRepository.findOrderDetailsByUserId(userId);

        return BuyHistoryDto.builder()
            .allCount(allCount)
            .processCount(processCount)
            .completeCount(completeCount)
            .orderDetails(orderDetailsDto)
            .build();
    }


    public BuyHistoryDto getRecentBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = buyingBiddingRepository.countCompleteByUserId(userId);

        List<OrderDetailsDto> orderDetailsDto = ordersRepository.findRecentOrderDetailsByUserId(
            userId, PageRequest.of(0, 3));

        return BuyHistoryDto.builder()
            .allCount(allCount)
            .processCount(processCount)
            .completeCount(completeCount)
            .orderDetails(orderDetailsDto)
            .build();
    }
}
