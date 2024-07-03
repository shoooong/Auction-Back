package com.example.backend.controller;

import com.example.backend.dto.user.*;
import com.example.backend.service.AccountService;
import com.example.backend.service.AddressService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Log4j2
public class MypageController {

    private final UserService userService;
    private final AddressService addressService;
    private final AccountService accountService;

    // 마이페이지 메인
    @GetMapping("")


    // 회원 정보 수정
    @PutMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestBody UserModifyDTO userModifyDTO) {
        log.info("UserModifyDTO: {}", userModifyDTO);

        userService.modifyUser(userModifyDTO);

        return ResponseEntity.ok("User information updated successfully!");
    }


    // 배송지 조회
    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAddress(@AuthenticationPrincipal UserDTO userDTO) {

        Long userId = userDTO.getUserId();

        List<AddressDTO> addressDTO = addressService.getAllAddress(userId);

        return ResponseEntity.ok(addressDTO);
    }

    // 배송지 수정
//    @PutMapping("/address/{addressId}")
//    public ResponseEntity<AddressDTO> modifyAddress(@PathVariable("addressId") Long addressId,
//                                                    @RequestBody AddressDTO addressDTO, @AuthenticationPrincipal UserDTO userDTO){
//
//        addressService.modifyAddress(addressId, addressDTO, userDTO.getUserId());
//
//        return ResponseEntity.ok(addressDTO);
//    }



    // 등록 계좌 조회
    @GetMapping("/account")
    public ResponseEntity<AccountDTO> getAccount(@AuthenticationPrincipal UserDTO userDTO) {

        Long userId = userDTO.getUserId();

        AccountDTO accountDTO =  accountService.getAccount(userId);

        return ResponseEntity.ok(accountDTO);
    }


    // 계좌 등록 및 수정
    @PutMapping("/account")
    public ResponseEntity<AccountDTO> registerOrModifyAccount(@AuthenticationPrincipal UserDTO userDTO, @RequestBody AccountReqDTO accountReqDTO) {

        log.info("AccountReqDTO: {}", accountReqDTO);

        Long userId = userDTO.getUserId();

        AccountDTO accountDTO = accountService.updateAccount(userId, accountReqDTO);

        return ResponseEntity.ok(accountDTO);
    }

}
