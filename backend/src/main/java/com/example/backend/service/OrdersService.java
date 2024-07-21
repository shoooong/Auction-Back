package com.example.backend.service;

import static com.example.backend.entity.enumData.OrderStatus.WAITING;

import com.example.backend.dto.mypage.buyHistory.BuyDetailsProcessDto;
import com.example.backend.dto.mypage.buyHistory.BuyHistoryAllDto;
import com.example.backend.dto.mypage.buyHistory.BuyDetailsDto;
import com.example.backend.dto.orders.AddressInfoDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.SaleOrderDto;
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
import com.example.backend.entity.enumData.SalesStatus;
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
    private final BuyingBiddingRepository buyingBiddingRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponService couponService;
    private final SalesBiddingRepository salesBiddingRepository;

    /**
     * 유저 기본 배송지 조회
     */

    public AddressInfoDto getDefaultAddress(Long userId){
        Address address = ordersRepository.findDefaultAddress(userId).orElseThrow(() -> new RuntimeException("Address not found"));
        return AddressInfoDto.builder()
            .name(address.getName())
            .addrPhone(address.getAddrPhone())
            .zonecode(address.getZonecode())
            .roadAddress(address.getRoadAddress())
            .detailAddress(address.getDetailAddress())
            .build();
    }

    /**
     * BuyingBidding order 생성
     */
    @Transactional
    public Orders createBuyOrder(UserDTO userDto, BuyOrderDto buyOrderDto) {
        Users user = userRepository.findById(userDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        BuyingBidding buyingBidding = buyingBiddingRepository.findById(
                buyOrderDto.getBuyingBiddingId())
            .orElseThrow(() -> new RuntimeException("BuyingBidding not found"));

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
            .build();

        return ordersRepository.save(order);
    }

    /**
     * SalesBidding order 생성
     */

    @Transactional
    public Orders createSaleOrder(UserDTO userDto, SaleOrderDto saleOrderDto) {
        Users user = userRepository.findById(userDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        SalesBidding salesBidding = salesBiddingRepository.findById(
                saleOrderDto.getSalesBiddingId())
            .orElseThrow(() -> new RuntimeException("SalesBidding not found"));

        BigDecimal totalAmount = salesBidding.getSalesBiddingPrice();
        Coupon coupon = null;

//        if (saleOrderDto.getCouponId() != null) {
//            coupon = couponRepository.findById(saleOrderDto.getCouponId())
//                .orElseThrow(() -> new RuntimeException("Coupon not found"));
//            CouponIssue userCoupon = couponIssueRepository.findByUsersAndCouponAndUseStatusFalse(
//                    user, coupon)
//                .orElseThrow(() -> new RuntimeException("Coupon not valid"));
//            totalAmount = couponService.applyCoupon(user, coupon, totalAmount)
//                .setScale(2, RoundingMode.HALF_UP);
//
//            userCoupon.useCoupon(true);
//            couponIssueRepository.save(userCoupon);
//        }
//
//        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
//            totalAmount = BigDecimal.ZERO;
//        }

        // SalesBidding의 상태에 따라 OrderStatus 설정

        Orders order = Orders.builder()
            .user(user)
            .product(salesBidding.getProduct())
            .salesBidding(salesBidding)
//            .coupon(coupon)
            .orderStatus(
                (salesBidding.getSalesStatus() == SalesStatus.COMPLETE)
                    ? OrderStatus.COMPLETE
                    : OrderStatus.WAITING)
            .orderPrice(totalAmount)
            .build();

        return ordersRepository.save(order);
    }


    /**
     * 구매 내역
     * 전체/입찰 중/종료 건수 및 조건별 구매 내역 (상품사진, 상품명, 상품사이즈, 결제금액, 주문상태) 주문날짜 기준 최신순 정렬
     */
    public BuyHistoryAllDto getAllBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = ordersRepository.countCompleteByUserId(userId);

        List<BuyDetailsDto> buyDetailsDto = ordersRepository.findAllOBuyDetails(userId);

        return BuyHistoryAllDto.builder()
            .allCount(allCount)
            .processCount(processCount)
            .completeCount(completeCount)
            .buyingDetails(buyDetailsDto)
            .build();
    }

    public List<BuyDetailsProcessDto> getBuyHistoryProcess(Long userId) {
        return ordersRepository.findBuyDetailsProcess(userId);
    }

    public List<BuyDetailsDto> getBuyHistoryComplete(Long userId) {
        return ordersRepository.findBuyDetailsComplete(userId);
    }

    public BuyHistoryAllDto getRecentBuyHistory(Long userId) {
        Long allCount = ordersRepository.countByUserUserId(userId);
        Long processCount = buyingBiddingRepository.countProcessByUserId(userId);
        Long completeCount = ordersRepository.countCompleteByUserId(userId);

        List<BuyDetailsDto> buyDetailsDto = ordersRepository.findRecentOrderDetails(
            userId, PageRequest.of(0, 3));

        return BuyHistoryAllDto.builder()
            .allCount(allCount)
            .processCount(processCount)
            .completeCount(completeCount)
            .buyingDetails(buyDetailsDto)
            .build();
    }
}
