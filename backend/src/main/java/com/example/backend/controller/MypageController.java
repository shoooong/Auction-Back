package com.example.backend.controller;

import com.example.backend.dto.mypage.accountSettings.AccountDTO;
import com.example.backend.dto.mypage.accountSettings.AccountReqDTO;
import com.example.backend.dto.mypage.accountSettings.SalesSummaryRespDto;
import com.example.backend.dto.mypage.addressSettings.AddressDto;
import com.example.backend.dto.mypage.addressSettings.AddressReqDto;
import com.example.backend.dto.mypage.buyHistory.BuyDetailsDto;
import com.example.backend.dto.mypage.buyHistory.BuyDetailsProcessDto;
import com.example.backend.dto.mypage.main.BookmarkProductsDto;
import com.example.backend.dto.mypage.main.MypageMainDto;
import com.example.backend.dto.mypage.saleHistory.SaleHistoryDto;
import com.example.backend.dto.mypage.drawHistory.DrawHistoryDto;
import com.example.backend.dto.mypage.buyHistory.BuyHistoryAllDto;
import com.example.backend.dto.user.*;
import com.example.backend.service.*;
import com.example.backend.service.luckyDraw.DrawService;
import com.example.backend.service.mypage.AccountService;
import com.example.backend.service.mypage.AddressService;
import com.example.backend.service.mypage.BookmarkProductService;
import com.example.backend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Log4j2
public class MypageController {

    private final UserService userService;
    private final AddressService addressService;
    private final AccountService accountService;
    private final OrdersService ordersService;
    private final SalesBiddingService salesBiddingService;
    private final BuyingBiddingService buyingBiddingService;
    private final DrawService drawService;
    private final BookmarkProductService bookmarkProductService;
    private final AdminService adminService;

    /**
     * 마이페이지 메인
     */
    @GetMapping("")
    public ResponseEntity<MypageMainDto> getMyPage(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        MypageMainDto mypageMainDto = userService.getMyPageInfo(userId);

        return ResponseEntity.ok(mypageMainDto);
    }


    /**
     * 내 정보 - 프로필 관리
     * 회원 정보 수정
     */
    @GetMapping("/modify")
    public ResponseEntity<UserModifyResDto> getUser(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        UserModifyResDto userModifyResDto = userService.getUser(userId);

        return ResponseEntity.ok(userModifyResDto);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestPart(required = false) MultipartFile file,
                                             @RequestPart UserModifyReqDto userModifyReqDto) {

        userService.modifyUser(userModifyReqDto, file);

        return ResponseEntity.ok("User information updated successfully!");
    }


    /**
     * 내 정보 - 배송지
     * 배송지 조회, 등록, 수정, 삭제
     */
    // 배송지 조회
    @GetMapping("/address")
    public List<AddressDto> getAddress(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        return addressService.getAllAddress(userId);
    }

    // 배송지 등록
    @PostMapping("/address")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressReqDto addressReqDto, @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        log.info("addAddress AddressReqDto: {}", addressReqDto);
        AddressDto addressDto = addressService.addAddress(userId, addressReqDto);

        return ResponseEntity.ok(addressDto);
    }

    // 배송지 수정
    @PutMapping("/address")
    public ResponseEntity<AddressDto> modifyAddress(@Valid @RequestBody AddressReqDto addressReqDto, @RequestParam Long addressId, @AuthenticationPrincipal UserDTO userDTO){
        Long userId = userDTO.getUserId();
        log.info("modifyAddress AddressReqDto: {}", addressReqDto);
        AddressDto addressDto = addressService.updateAddress(userId, addressId, addressReqDto);

        return ResponseEntity.ok(addressDto);
    }

    // 배송지 삭제
    @DeleteMapping("/address")
    public ResponseEntity<String> deleteAddress(@RequestParam Long addressId, @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        addressService.deleteAddress(userId, addressId);

        return ResponseEntity.ok("Address deleted successfully!");
    }


    /**
     * 내 정보 - 계좌
     * 계좌 조회 및 등록(수정)
     */
    // 등록 계좌 조회
    @GetMapping("/account")
    public ResponseEntity<AccountDTO> getAccount(@AuthenticationPrincipal UserDTO userDTO) {

        Long userId = userDTO.getUserId();

        AccountDTO accountDTO =  accountService.getAccount(userId);

        return ResponseEntity.ok(accountDTO);
    }

    // 계좌 등록 및 수정
    @PostMapping("/account")
    public ResponseEntity<AccountDTO> registerOrModifyAccount(@RequestBody AccountReqDTO accountReqDTO, @AuthenticationPrincipal UserDTO userDTO) {

        log.info("AccountReqDTO: {}", accountReqDTO);

        Long userId = userDTO.getUserId();

        AccountDTO accountDTO = accountService.updateAccount(userId, accountReqDTO);

        return ResponseEntity.ok(accountDTO);
    }

    // 판매 정산 내역
    @GetMapping("/account/sales/user")
    public ResponseEntity<SalesSummaryRespDto> getSalesSummary(@AuthenticationPrincipal UserDTO user, Pageable pagable){

        Long userId = user.getUserId();
        log.info("userId: {}",userId);

        SalesSummaryRespDto result =adminService.getSalesSummary(userId,pagable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 쇼핑 정보 - 구매 내역
     * 전체, 입찰 중, 종료 건수
     * 주문 날짜 최신순 정렬
     */
    // 전체
    @GetMapping("/buyHistory")
    public ResponseEntity<BuyHistoryAllDto> getAllBuyHistory(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        BuyHistoryAllDto buyHistoryAllDto = ordersService.getAllBuyHistory(userId);

        return ResponseEntity.ok(buyHistoryAllDto);
    }
    // 입찰 중
    @GetMapping("/buyHistory/process")
    public List<BuyDetailsProcessDto> getBuyHistoryProcess(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        return ordersService.getBuyHistoryProcess(userId);
    }
    // 종료
    @GetMapping("/buyHistory/complete")
    public List<BuyDetailsDto> getBuyHistoryComplete(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        return ordersService.getBuyHistoryComplete(userId);
    }
    // 입찰 취소
    @PutMapping("/buyHistory/process")
    public ResponseEntity<String> cancelBuyingBidding(@RequestParam Long buyingBiddingId, @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        buyingBiddingService.cancelBuyingBidding(userId, buyingBiddingId);

        return ResponseEntity.ok("BuyingBidding canceled successfully!");
    }


    /**
     * 쇼핑 정보 - 판매 내역
     * 전체, 검수 중, 진행 중, 종료 건수
     * 판매 입찰 시간 최신순 정렬
     */
    // 상태별 필터
    @GetMapping("/saleHistory")
    public ResponseEntity<SaleHistoryDto> getSaleHistory(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        SaleHistoryDto saleHistoryDTO = salesBiddingService.getSaleHistory(userId);

        return ResponseEntity.ok(saleHistoryDTO);
    }
    // 입찰 취소
    @PutMapping("/saleHistory")
    public ResponseEntity<String> cancelSalesBidding(@RequestParam Long salesBiddingId, @AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        salesBiddingService.cancelSalesBidding(userId, salesBiddingId);

        return ResponseEntity.ok("SalesBidding canceled successfully!");
    }


    /**
     * 쇼핑 정보 - 응모 내역
     * 전체, 진행 중, 당첨 건수
     * 럭키드로우 응모 내역 전체 조회 (당첨발표일 최신순 정렬)
     */
    // TODO: 추후 당첨, 미당첨 필터 추가
    @GetMapping("/drawHistory")
    public ResponseEntity<DrawHistoryDto> getDrawHistory(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        DrawHistoryDto drawHistoryDto = drawService.getDrawHistory(userId);

        return ResponseEntity.ok(drawHistoryDto);
    }


    /**
     * 쇼핑 정보 - 관심
     * 1) 관심 상품
     */
    @GetMapping("/bookmark/product")
    public List<BookmarkProductsDto> getBookmarkProduct(@AuthenticationPrincipal UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        return bookmarkProductService.getAllBookmarkProducts(userId);
    }

    /**
     * 쇼핑 정보 - 관심
     * 2) 관심 스타일
     */
//    @GetMapping("/bookmark/style")


}
