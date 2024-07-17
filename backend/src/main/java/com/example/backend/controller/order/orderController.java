package com.example.backend.controller.order;

import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.orders.SaleOrderDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Log4j2
public class orderController {


    private final OrdersService ordersService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyOrder(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {
//        buyOrderDto.setUserId(userDTO.getUserId());
        ordersService.createBuyOrder(userDTO, buyOrderDto);

        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
    }

    @PostMapping("/sales")
    public ResponseEntity<?> salesOrder(@AuthenticationPrincipal UserDTO userDTO, @RequestBody
    SaleOrderDto saleOrderDto) {
        ordersService.createSaleOrder(userDTO, saleOrderDto);

        return new ResponseEntity<>(saleOrderDto, HttpStatus.OK);
    }
}
