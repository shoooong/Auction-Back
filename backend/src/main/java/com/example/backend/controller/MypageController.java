package com.example.backend.controller;

import com.example.backend.dto.user.AddressDTO;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.dto.user.UserModifyDTO;
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

    // 마이페이지 메인
    @GetMapping("")


    // 회원 정보 수정
    @PutMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestBody UserModifyDTO userModifyDTO){
        log.info("UserModifyDTO: {}", userModifyDTO);

        userService.modifyUser(userModifyDTO);

        return ResponseEntity.ok("User information updated successfully!");
    }


//    @PutMapping("/modify")
//    public ResponseEntity<String> modifyUser(@RequestBody UserModifyDTO userModifyDTO){
//        log.info("UserModifyDTO: {}", userModifyDTO);
//
//        try {
//            if (userModifyDTO.isSocial()){
//                userService.modifyPasswordOnly(passwordModifyDTO);
//            } else {
//                userService.modifyUser(userModifyDTO);
//            }
//
//            userService.modifyUser(userModifyDTO);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return ResponseEntity.ok("User information updated successfully!");
//    }



    // 배송지 조회
    @GetMapping("/address")
    public List<AddressDTO> getAddress(@AuthenticationPrincipal UserDTO userDTO){

        log.info("UserDTO: {}", userDTO);
        log.info("userId: {}", userDTO.getUserId());
        List<AddressDTO> addressDTO = addressService.getAllAddress(userDTO.getUserId());

        return addressDTO;
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

}
