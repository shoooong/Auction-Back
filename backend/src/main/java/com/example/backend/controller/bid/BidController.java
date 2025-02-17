package com.example.backend.controller.bid;

import com.example.backend.dto.orders.BiddingRequestDto;
import com.example.backend.dto.orders.BuyNowDto;
import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.SaleOrderDto;
import com.example.backend.dto.orders.SalesNowDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.BuyingBiddingService;
import com.example.backend.service.SalesBiddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bid")
@Log4j2
public class BidController {

    private final BuyingBiddingService buyingBiddingService;
    private final SalesBiddingService salesBiddingService;

//    @PostMapping("/buyingBidding/register")
//    public ResponseEntity<?> buyingBidding(@AuthenticationPrincipal UserDTO userDTO,
//        @RequestBody BiddingRequestDto buyingInfo) {
//
//        buyingBiddingService.registerBuyingBidding(userDTO, buyingInfo);
//
//        return new ResponseEntity<>(buyingInfo, HttpStatus.OK);
//    }

    @PostMapping("/buyingBidding/register")
    public ResponseEntity<?> buyingBidding(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {

        Long orderId = buyingBiddingService.registerBuyingBidding(userDTO, buyOrderDto);

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    //    @PostMapping("/salesBidding/register")
//    public ResponseEntity<?> salesBidding(@AuthenticationPrincipal UserDTO userDTO,
//        @RequestBody BiddingRequestDto buyingInfo) {
//
//        salesBiddingService.registerSalesBidding(userDTO, buyingInfo);
//
//        return new ResponseEntity<>(buyingInfo, HttpStatus.OK);
//    }
    @PostMapping("/salesBidding/register")
    public ResponseEntity<?> salesBidding(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody SaleOrderDto saleOrderDto) {

        Long orderId = salesBiddingService.registerSalesBidding(userDTO, saleOrderDto);

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }


    @PostMapping("/buy")
    public ResponseEntity<?> buyBid(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {
//        .createBuyOrder(userDTO, buyOrderDto);

        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
    }

    @PostMapping("/sales")
    public ResponseEntity<?> sellBid(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {
//        .createBuyOrder(us erDTO, buyOrderDto);

        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
    }

    @PostMapping("/buyNow")
    public ResponseEntity<?> buyNow(@AuthenticationPrincipal UserDTO userDTO, @RequestBody BuyNowDto buyNowDto) {
        Long applyBuy = buyingBiddingService.applyBuyNow(userDTO, buyNowDto);

        return new ResponseEntity<>(applyBuy, HttpStatus.OK);
    }

    @PostMapping("/saleNow")
    public ResponseEntity<?> salesNow(@AuthenticationPrincipal UserDTO userDTO, @RequestBody SalesNowDto salesNowDto){
        Long applySale = salesBiddingService.applySaleNow(userDTO, salesNowDto);
        return new ResponseEntity<>(applySale, HttpStatus.OK);
    }
}
