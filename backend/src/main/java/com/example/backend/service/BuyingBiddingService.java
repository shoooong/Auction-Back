package com.example.backend.service;

import static com.example.backend.entity.enumData.BiddingStatus.PROCESS;
import static com.example.backend.entity.enumData.SalesStatus.COMPLETE;

import com.example.backend.dto.orders.BiddingRequestDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.BuyingBiddingDto;
import com.example.backend.dto.orders.OrderProductDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Address;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Coupon;
import com.example.backend.entity.CouponIssue;
import com.example.backend.entity.Orders;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.OrderStatus;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.CouponIssue.CouponIssueRepository;
import com.example.backend.repository.Orders.OrdersRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.coupon.CouponRepository;
import com.example.backend.repository.mypage.AddressRepository;
import com.example.backend.service.coupon.CouponService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyingBiddingService {

    private final OrdersRepository ordersRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final BuyingBiddingRepository buyingBiddingRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponService couponService;
    private final SalesBiddingRepository salesBiddingRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;


    /**
     *  구매입찰 등록
     */
//    public void registerBuyingBidding(UserDTO userDTO, BiddingRequestDto buyingInfo) {
//
//        Product product = productRepository.findById(buyingInfo.getProductId())
//            .orElseThrow(() -> new RuntimeException("Product not valid"));
//
//        Users user = userRepository.findById(userDTO.getUserId())
//            .orElseThrow(() -> new RuntimeException("User not valid"));
//
//        BuyingBidding buyingBidding = BuyingBidding.builder()
//            .buyingBiddingPrice(buyingInfo.getPrice())
//            .product(product)
//            .user(user)
//            .buyingBiddingTime(LocalDateTime.now().plusDays(buyingInfo.getExp()))
//            .biddingStatus(PROCESS)
//            .build();
//
//        buyingBiddingRepository.save(buyingBidding);
//    }

    /**
     * 구매입찰
     */
    @Transactional
    public Long registerBuyingBidding(UserDTO userDTO, BuyOrderDto buyOrderDto) {

        Product product = productRepository.findById(buyOrderDto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not valid"));

        Users user = userRepository.findById(userDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not valid"));

        BuyingBidding buyingBidding = BuyingBidding.builder()
            .buyingBiddingPrice(buyOrderDto.getPrice())
            .product(product)
            .user(user)
            .buyingBiddingTime(LocalDateTime.now().plusDays(buyOrderDto.getExp()))
            .biddingStatus(PROCESS)
            .build();

        buyingBiddingRepository.save(buyingBidding);

        Address address = addressRepository.findById(buyOrderDto.getAddressId()).orElseThrow(()->new RuntimeException("Address not found"));

        BigDecimal totalAmount = buyingBidding.getBuyingBiddingPrice(); // 입찰 가격 가져옴
        Coupon coupon = null;

        if (buyOrderDto.getCouponId() != null) { // 쿠폰 사용 확인, 적용
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

        Orders order = Orders.builder() // order 데이터 생성 후 저장
            .user(user)
            .product(buyingBidding.getProduct())
            .buyingBidding(buyingBidding)
            .coupon(coupon)
            .orderStatus((buyingBidding.getBiddingStatus() == BiddingStatus.COMPLETE)
                ? OrderStatus.COMPLETE
                : OrderStatus.WAITING)
            .orderPrice(totalAmount)
            .address((address))
            .build();

        buyingBidding.changeBiddingStatus(BiddingStatus.COMPLETE);

        Long orderId = ordersRepository.save(order).getOrderId();

        return orderId;
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


    @Transactional
    public void cancelBuyingBidding(Long userId, Long buyingBiddingId) {
        BuyingBidding buyingBidding = buyingBiddingRepository.findByBuyingBiddingIdAndUserUserId(
                buyingBiddingId, userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매 입찰 내역입니다."));

        buyingBidding.changeBiddingStatus(BiddingStatus.CANCEL);
        buyingBiddingRepository.save(buyingBidding);

        Optional<Orders> optOrder = ordersRepository.findByBuyingBiddingBuyingBiddingId(
            buyingBiddingId);
        optOrder.ifPresent(order -> {
            order.changeOrderStatus(OrderStatus.CANCEL);
            ordersRepository.save(order);
        });
    }
}
