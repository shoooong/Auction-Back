package com.example.backend.service;

import com.example.backend.dto.mypage.addressSettings.AddressDto;
import com.example.backend.dto.orders.AddressInfoDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.BuyingBiddingDto;
import com.example.backend.dto.orders.OrderDto;
import com.example.backend.dto.orders.OrderProductDto;
import com.example.backend.dto.orders.SaleOrderDto;
import com.example.backend.dto.orders.SalesBiddingDto;
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
import com.example.backend.repository.mypage.AddressRepository;
import com.example.backend.service.coupon.CouponService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    private final AddressRepository addressRepository;

    /**
     * 유저 기본 배송지 조회
     */

    public AddressInfoDto getDefaultAddress(Long userId){
        Address address = ordersRepository.findDefaultAddress(userId).orElse(null);

        if(address == null) { return null; }
        return AddressInfoDto.builder()
            .addressId(address.getAddressId())
            .name(address.getName())
            .addrPhone(address.getAddrPhone())
            .zonecode(address.getZonecode())
            .roadAddress(address.getRoadAddress())
            .detailAddress(address.getDetailAddress())
            .build();
    }


    public OrderDto getOrderOne(Long orderId) {
        Orders order = ordersRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDto.OrderDtoBuilder orderDtoBuilder = OrderDto.builder()
            .orderId(order.getOrderId())
            .orderPrice(order.getOrderPrice())
            .orderStatus(order.getOrderStatus())
            .orderDate(order.getCreateDate())
            .product(new OrderProductDto().fromEntity(order.getProduct()))
            .address(new AddressDto().fromEntity(order.getAddress()));

        orderDtoBuilder.biddingBidding(
            Optional.ofNullable(order.getBuyingBidding())
                .map(buyingBidding -> new BuyingBiddingDto().fromEntity(buyingBidding))
                .orElse(new BuyingBiddingDto())
        );

        orderDtoBuilder.salesBidding(
            Optional.ofNullable(order.getSalesBidding())
                .map(salesBidding -> new SalesBiddingDto().fromEntity(salesBidding))
                .orElse(new SalesBiddingDto())
        );
        return orderDtoBuilder.build();
    }


    /**
     * BuyingBidding order 생성
     */
//    @Transactional
//    public Orders createBuyOrder(UserDTO userDto, BuyOrderDto buyOrderDto) {
//        Users user = userRepository.findById(userDto.getUserId())
//            .orElseThrow(() -> new RuntimeException("User not found"));
//        BuyingBidding buyingBidding = buyingBiddingRepository.findById(
//                buyOrderDto.getBuyingBiddingId())
//            .orElseThrow(() -> new RuntimeException("BuyingBidding not found"));
//        Address address = addressRepository.findById(buyOrderDto.getAddressId()).orElseThrow(()->new RuntimeException("Address not found"));
//
//
//        BigDecimal totalAmount = buyingBidding.getBuyingBiddingPrice(); // 입찰 가격 가져옴
//        Coupon coupon = null;
//
//        if (buyOrderDto.getCouponId() != null) { // 쿠폰 사용 확인, 적용
//            coupon = couponRepository.findById(buyOrderDto.getCouponId())
//                .orElseThrow(() -> new RuntimeException("Coupon not found"));
//            CouponIssue userCoupon = couponIssueRepository.findByUsersAndCouponAndUseStatusFalse(
//                    user, coupon)
//                .orElseThrow(() -> new RuntimeException("Coupon not valid"));
//            totalAmount = couponService.applyCoupon(user, coupon, totalAmount)
//                .setScale(2, RoundingMode.HALF_UP);
//
//            userCoupon.useCoupon(true);
//            userCoupon.useDate();
//            couponIssueRepository.save(userCoupon);
//        }
//
//        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
//            totalAmount = BigDecimal.ZERO;
//        }
//
//        Orders order = Orders.builder() // order 데이터 생성 후 저장
//            .user(user)
//            .product(buyingBidding.getProduct())
//            .buyingBidding(buyingBidding)
//            .coupon(coupon)
//            .orderStatus((buyingBidding.getBiddingStatus() == COMPLETE)
//                ? OrderStatus.COMPLETE
//                : OrderStatus.WAITING)
//            .orderPrice(totalAmount)
//            .address((address))
//            .build();
//
//        buyingBidding.changeBiddingStatus(COMPLETE);
//
//
//        return ordersRepository.save(order);
//    }

    /**
     * SalesBidding order 생성
     */
//
//    @Transactional
//    public Orders createSaleOrder(UserDTO userDto, SaleOrderDto saleOrderDto) {
//        Users user = userRepository.findById(userDto.getUserId())
//            .orElseThrow(() -> new RuntimeException("User not found"));
//        SalesBidding salesBidding = salesBiddingRepository.findById(
//                saleOrderDto.getSalesBiddingId())
//            .orElseThrow(() -> new RuntimeException("SalesBidding not found"));
//        Address address = addressRepository.findById(saleOrderDto.getAddressId()).orElseThrow(()->new RuntimeException("Address not found"));
//
//        BigDecimal totalAmount = salesBidding.getSalesBiddingPrice();
//        Coupon coupon = null;
//
//        // SalesBidding의 상태에 따라 OrderStatus 설정
//
//        Orders order = Orders.builder()
//            .user(user)
//            .product(salesBidding.getProduct())
//            .salesBidding(salesBidding)
////            .coupon(coupon)
//            .orderStatus(
//                (salesBidding.getSalesStatus() == SalesStatus.COMPLETE)
//                    ? OrderStatus.COMPLETE
//                    : OrderStatus.WAITING)
//            .orderPrice(totalAmount)
//            .address(address)
//            .build();
//
//        salesBidding.changeSalesStatus(SalesStatus.COMPLETE);
//
//
//        return ordersRepository.save(order);
//    }
}
