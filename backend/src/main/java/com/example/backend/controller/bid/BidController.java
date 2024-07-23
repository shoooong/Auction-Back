package com.example.backend.controller.bid;

import com.example.backend.dto.orders.BuyOrderDto;
import com.example.backend.dto.user.UserDTO;
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
@RequestMapping("/bid")
@Log4j2
public class BidController {

    @PostMapping("/buy")
    public ResponseEntity<?> buyBid(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {
//        .createBuyOrder(userDTO, buyOrderDto);

        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
    }
    @PostMapping("/sales")
    public ResponseEntity<?> sellBid(@AuthenticationPrincipal UserDTO userDTO,
        @RequestBody BuyOrderDto buyOrderDto) {
//        .createBuyOrder(userDTO, buyOrderDto);

        return new ResponseEntity<>(buyOrderDto, HttpStatus.OK);
    }

}
