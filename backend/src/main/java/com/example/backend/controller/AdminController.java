package com.example.backend.controller;

import com.example.backend.dto.requestProduct.RequestProductRespDto;
import com.example.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;


    // RequestProduct 테이블에 isApproval == false 인 상품만 주세요.[요청상품 리스트]
    @GetMapping("/unapproved")
    public ResponseEntity<?> getListRequest() {
        RequestProductRespDto.ReqProductListRespDto requestProductRespDto = adminService.getUnapprovedProducts();
        return new ResponseEntity<>(requestProductRespDto, HttpStatus.OK);
    }

    // RequestProduct 테이블에 requestId == 클라이언트가 요청한 ID 인 상품만 주세요.[요청상품 단건]
    @GetMapping("/unapproved/{requestId}")
    public ResponseEntity<?> getOneRequest(@PathVariable Long requestId) {
        log.info("requestId::::::" +requestId);
        RequestProductRespDto.ReqProductRespDto requestProductRespDto = adminService.getOneUnapprovedProduct(requestId);
        return new ResponseEntity<>(requestProductRespDto, HttpStatus.OK);
    }


    //상품 등록
    @PostMapping("/unapproved/{requestId}/approve")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId) {
        log.info("requestId::::" +requestId);
        RequestProductRespDto.ReqProductSaveRespDto reqProductSaveRespDto = adminService.registerReqProduct(requestId);
        return new ResponseEntity<>(reqProductSaveRespDto, HttpStatus.OK);
    }


}
