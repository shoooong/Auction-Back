package com.example.backend.controller;


import com.example.backend.dto.admin.AdminProductDto;
import com.example.backend.dto.admin.AdminProductRespDto;
import com.example.backend.dto.admin.AdminRespDto;
import com.example.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //관리자 상품 카테고리별 조회(대분류, 소분류)
    @GetMapping("/products/{mainDepartment}")
    public ResponseEntity<?> getProductsByDepartment(@PathVariable String mainDepartment, @RequestParam(value = "subDepartment", required = false) String subDepartment) {

        List<AdminProductDto> products= adminService.getProducts(mainDepartment, subDepartment);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //관리자 상품 상세 조회(구매입찰 + 판매입찰)
    @GetMapping("/products/detailed/{modelNum}")
    public ResponseEntity<?> findDetailedProduct(@PathVariable String modelNum, @RequestParam(value = "productSize", required = false) String productSize) {
        log.info("$$$$$$$$$$$$$$");
        log.info("modelNum{} productSize{}", modelNum, productSize);

        //modelnum을 이용해서 상품 상세 조회, size에 따라 카테고리 조회하듯이 입찰 정보 불러오기
        List<AdminProductRespDto> detailedProduct =  adminService.getDetailProduct(modelNum, productSize);
        return new ResponseEntity<>(detailedProduct,HttpStatus.OK);

    }

    //판매입찰(검수중) 상품 검수 승인
    //위에서 판매입찰 id를 받아와서 해당 판매입찰 상태 변경
    @PostMapping("/sales/{salesBiddingId}/approve")
    public ResponseEntity<?> acceptSaleBidding(@PathVariable Long salesBiddingId){
        //salesBiddingId를 통하여 검수요청중인 salesStatus = INSPECTION ->PROCESS 으로 변경

        adminService.acceptSales(salesBiddingId);

        return new ResponseEntity<>("",HttpStatus.OK);
    }


}
