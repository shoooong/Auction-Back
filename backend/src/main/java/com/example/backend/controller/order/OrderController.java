package com.example.backend.controller.order;

import com.example.backend.dto.orders.AddressInfoDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.BuyingBiddingDto;
import com.example.backend.dto.orders.OrderDto;
import com.example.backend.dto.orders.OrderProductDto;
import com.example.backend.dto.orders.PaymentDto;
import com.example.backend.dto.orders.SaleOrderDto;
import com.example.backend.dto.orders.SalesBiddingDto;
import com.example.backend.dto.product.ProductResponseDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.SalesBidding;
import com.example.backend.service.BuyingBiddingService;
import com.example.backend.service.OrdersService;
import com.example.backend.service.Product.ProductService;
import com.example.backend.service.SalesBiddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Log4j2
public class OrderController {


    private final OrdersService ordersService;
    private final BuyingBiddingService buyingBiddingService;
    private final SalesBiddingService salesBiddingService;
    private final ProductService productService;

//    @PostMapping("/buy")
//    public ResponseEntity<?> buyOrder(@AuthenticationPrincipal UserDTO userDTO,
//        @RequestBody BuyOrderDto buyOrderDto) {
////        buyOrderDto.setUserId(userDTO.getUserId());
//        ordersService.createBuyOrder(userDTO, buyOrderDto);
//
//        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
//    }



    @GetMapping("/addr") // 배송지 반환
    public ResponseEntity<?> addrInfo(@AuthenticationPrincipal UserDTO userDTO){
        AddressInfoDto addressInfoDto = ordersService.getDefaultAddress(userDTO.getUserId());
        return new ResponseEntity<>(addressInfoDto, HttpStatus.OK);
    }

    @PostMapping("/sales") //
    public ResponseEntity<?> salesOrder(@AuthenticationPrincipal UserDTO userDTO, @RequestBody
    SaleOrderDto saleOrderDto) {
//        ordersService.createSaleOrder(userDTO, saleOrderDto);

        return new ResponseEntity<>(saleOrderDto, HttpStatus.OK);
    }


    // 구매 입찰 정보 가져오기(즉시판매)
    @GetMapping("/buy") // 결제 완료 후 주문 완료 페이지,
    public ResponseEntity<?> buyInfo(@RequestParam Long buyingBiddingId){
        BuyingBiddingDto buyingBiddingDto = buyingBiddingService.getBuyingBiddingDto(buyingBiddingId);
        return new ResponseEntity<>(buyingBiddingDto, HttpStatus.OK);
    }
    // 판매 입찰 정보 가져오기(즉시구매)
    @GetMapping("/sales") // 결제 완료 후 주문 완료 페이지
    public ResponseEntity<?> salesInfo(@RequestParam Long salesBiddingId){
        SalesBiddingDto salesBiddingDto = salesBiddingService.getSalesBiddingDto(salesBiddingId);
        return new ResponseEntity<>(salesBiddingDto, HttpStatus.OK);
    }

    @GetMapping("/toss/success")
    public ResponseEntity<?> tossSuccess(@AuthenticationPrincipal UserDTO userDTO, @RequestBody
        PaymentDto paymentDto){
        return null;
    }

    @GetMapping("/productOne")
    public ResponseEntity<?> getProduct(@AuthenticationPrincipal UserDTO userDTO, @RequestParam Long productId) {
        OrderProductDto orderProductDto = productService.getProductOne(productId);

        return new ResponseEntity<>(orderProductDto, HttpStatus.OK);


    }

    @GetMapping("/orderOne")
    public ResponseEntity<?> getOrder(@AuthenticationPrincipal UserDTO userDTO, @RequestParam Long orderId) {
        OrderDto orderDto = ordersService.getOrderOne(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }


}
