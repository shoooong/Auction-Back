package com.example.backend.controller;


import com.example.backend.dto.admin.AdminProductDto;
import com.example.backend.dto.admin.AdminRespDto;
import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
@RestController
public class AdminController {

    private final AdminService adminService;
    //요청상품 전체조회
    @GetMapping("/requests")
    public ResponseEntity<?> findReqProduct() {
        AdminRespDto.ReqProductsRespDto regProductRespDto = adminService.reqProducts();
        return new ResponseEntity<>(regProductRespDto, HttpStatus.OK);
    }

    //요청상품 단건 조회
    @GetMapping("/requests/{productId}")
    public ResponseEntity<?> findReqProduct(@PathVariable Long productId) {
        AdminRespDto.ReqProductRespDto reqProductRespDto = adminService.reqProduct(productId);
        return new ResponseEntity<>(reqProductRespDto, HttpStatus.OK);
    }

    //요청상품 판매상품으로 등록
    @PostMapping("/requests/{productId}")
    public ResponseEntity<?> acceptReqProduct(@PathVariable Long productId) {
        AdminRespDto.RegProductRespDto regProductRespDto = adminService.acceptRequest(productId);
        return new ResponseEntity<>(regProductRespDto,HttpStatus.OK);
    }

//    // 전체상품 카테고리별로 조회
//    @GetMapping("/products/{mainDepartment}")
//    public ResponseEntity<?> findProductByDepartment(@PathVariable String mainDepartment, @RequestParam(value = "subDepartment", required = false) String subDepartment) {
//        log.info("subDepartment {} mainDepartment{}", subDepartment, mainDepartment);
//
//        AdminRespDto.AdminProductsRespDto adminProductsRespDto = adminService.findProductByDepartment(mainDepartment, subDepartment);
//
//
//        return new ResponseEntity<>(adminProductsRespDto,HttpStatus.OK);
//    }

    @GetMapping("/products/{mainDepartment}")
    public ResponseEntity<?> getProducts(@PathVariable String mainDepartment, @RequestParam(value = "subDepartment", required = false) String subDepartment) {

        List<AdminProductDto> products= adminService.getProducts(mainDepartment, subDepartment);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
