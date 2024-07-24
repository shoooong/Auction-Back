package com.example.backend.controller.order;

import com.example.backend.dto.orders.AddressInfoDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.BuyingBiddingDto;
import com.example.backend.dto.orders.PaymentDto;
import com.example.backend.dto.orders.SaleOrderDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.BuyingBiddingService;
import com.example.backend.service.OrdersService;
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

    @PostMapping("/buy")
    public ResponseEntity<?> buyOrder(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {
//        buyOrderDto.setUserId(userDTO.getUserId());
        ordersService.createBuyOrder(userDTO, buyOrderDto);

        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
    }

    @GetMapping("/addr") // 배송지 반환
    public ResponseEntity<?> addrInfo(@AuthenticationPrincipal UserDTO userDTO){
        AddressInfoDto addressInfoDto = ordersService.getDefaultAddress(userDTO.getUserId());
        return new ResponseEntity<>(addressInfoDto, HttpStatus.OK);
    }

    @PostMapping("/sales") //
    public ResponseEntity<?> salesOrder(@AuthenticationPrincipal UserDTO userDTO, @RequestBody
    SaleOrderDto saleOrderDto) {
        ordersService.createSaleOrder(userDTO, saleOrderDto);

        return new ResponseEntity<>(saleOrderDto, HttpStatus.OK);
    }


    @GetMapping("/buy") // 결제 완료 후 주문 완료 페이지
    public ResponseEntity<?> buyInfo(@RequestParam Long buyingBiddingId){
        BuyingBiddingDto buyingBiddingDto = buyingBiddingService.getBuyingBiddingDto(buyingBiddingId);
        return new ResponseEntity<>(buyingBiddingDto, HttpStatus.OK);
    }

    @GetMapping("toss/success")
    public ResponseEntity<?> tossSuccess(@AuthenticationPrincipal UserDTO userDTO, @RequestBody
        PaymentDto paymentDto){
        return null;
    }


}
