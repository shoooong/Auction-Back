package com.example.backend.service;


import com.example.backend.dto.admin.*;
import com.example.backend.dto.feed.StyleFeedDto;
import com.example.backend.dto.luckyDraw.LuckyDrawsDto;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.LuckyProcessStatus;
import com.example.backend.entity.enumData.LuckyStatus;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.entity.enumData.SalesStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.LuckyDraw.LuckyDrawRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.service.objectstorage.ObjectStorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class AdminService {

    private final ProductRepository productRepository;
    private final SalesBiddingRepository salesBiddingRepository;
    private final LuckyDrawRepository luckyDrawRepository;
    private final UserRepository userRepository;
    private final ObjectStorageService objectStorageService;

    //요청상품 다건 조회
    public AdminRespDto.ReqProductsRespDto reqProducts(){
        List<Product> products = productRepository.findByProductStatus(ProductStatus.REQUEST);
        return new AdminRespDto.ReqProductsRespDto(products);

    }

    // 요청상품 단건 조회
    public AdminRespDto.ReqProductRespDto reqProduct(Long productId) {
        //productId로 상품찾기
        Optional<Product> reqProduct = productRepository.findById(productId);
        Product result = reqProduct.orElseThrow();
        return new AdminRespDto.ReqProductRespDto(result);
    }
    //요청상품 판매상품으로 등록
    @Transactional
    public AdminRespDto.RegProductRespDto acceptRequest(Long productId) {
        Optional<Product> result = productRepository.findByProductIdAndProductStatus(productId,ProductStatus.REQUEST);
        Product request = result.orElseThrow();

        //모델번호 + 사이즈로 중복값이 있는지 판별
        List<Product> registeredProducts = productRepository.findByProductStatus(ProductStatus.REGISTERED);
        Product finalRequest = request;
        boolean isDuplicate = registeredProducts.stream().anyMatch(registered ->
                finalRequest.getModelNum().equals(registered.getModelNum()) &&
                        finalRequest.getProductSize().equals(registered.getProductSize())
        );
        if (isDuplicate) {
            // 중복이면
            throw new RuntimeException("이미 기존 상품에 등록되어 있습니다.");
        } else {

            finalRequest.changeProductStatus(ProductStatus.REGISTERED);
            return new AdminRespDto.RegProductRespDto(finalRequest);
        }
    }

    //판매상품 관리(카테고리별)조회
    public AdminRespDto.AdminProductResponseDto getProducts(String mainDepartment, String subDepartment) {
        List<AdminProductDto> adminProductDto = productRepository.getProductsByDepartment(mainDepartment, subDepartment);

        log.info("productId" + adminProductDto.get(0).getProductId());
        return new AdminRespDto.AdminProductResponseDto(mainDepartment,subDepartment,adminProductDto);

    }

    //상품상세 조회 + 판매입찰 + 구매입찰 정보
    public AdminRespDto.AdminProductDetailRespDto getDetailProduct(String modelNum, String productSize) {

        log.info("modelNum{} productSize{}", modelNum, productSize);
        /*
        1. modelNum 기준으로 중복없이 상품 상세 정보 조회,
        2. 상품 사이즈별로 수량,입찰(판매, 구매) 정보 같이 가져오기
        * */
        List<AdminProductRespDto> detailedProduct = productRepository.getDetailedProduct(modelNum,productSize);

//        return detailedProduct;
        return new AdminRespDto.AdminProductDetailRespDto(modelNum, productSize, detailedProduct);
    }

    //검수 승인 처리
    @Transactional
    public AdminRespDto.ChangeRespDto acceptSales(Long salesBiddingId) {

        //해당 id의 판매입찰 정보 찾기
        Optional<SalesBidding> salesBidding = salesBiddingRepository.findById(salesBiddingId);
        SalesBidding acceptSales = salesBidding.orElseThrow();

        //판매입찰 상태 검수 -> 판매중으로 변경
        acceptSales.changeSalesStatus(SalesStatus.PROCESS);

        //판매입찰의 상품아이디 가져오기
        Long productId = acceptSales.getProduct().getProductId();

        //해당 상품의 productId를 가진 상품의 수량 증가
        Optional<Product> selectProduct = productRepository.findById(productId);
        Product product = selectProduct.orElseThrow();
        product.addQuantity(1);

        return new AdminRespDto.ChangeRespDto(acceptSales, product);

    }

    //관리자 럭키드로우 상품 등록
    @Transactional
    public AdminReqDto.AdminLuckDrawDto insertLucky(AdminReqDto.AdminLuckDrawDto adminLuckDrawDto) {

        String bucketName = "push";
        String directoryPath = "shooong/luckydraw";
        // S3에 이미지 업로드
        String imageUrl = objectStorageService.uploadFile(bucketName, directoryPath, adminLuckDrawDto.getLuckyphoto());

        //관리자가 luckyDrawDto 폼에 등록한 변수
        LuckyDraw luckyDraw = LuckyDraw.builder()
                .luckyName(adminLuckDrawDto.getLuckyName())
                .content(adminLuckDrawDto.getContent())
                .luckyImage(imageUrl)
                .luckyProcessStatus(LuckyProcessStatus.READY)
                .luckyPeople(adminLuckDrawDto.getLuckyPeople())
                .build();

        LuckyDraw insertLucky = luckyDrawRepository.save(luckyDraw);
        return new AdminReqDto.AdminLuckDrawDto(insertLucky);
    }

    //test
    //매주 첫째주 11시에 시작
//    @Scheduled(cron = "0 0 11 ? * MON")
    @Scheduled(cron = "0 50 19 * * FRI")
    @Transactional
    public void cronJob() {
        //스케줄 실행시, 데이터 베이스에 저장되어 있는 럭키드로우 데이터 startDate, endDate, LuckDate 등록

        //LuckyProcessStatus = READY인 상품 조회
        List<LuckyDraw> ready = luckyDrawRepository.findByLuckyProcessStatus(LuckyProcessStatus.READY);

        //시작 날짜 매주 월요일 11:00:00
        LocalDateTime startDate = LocalDateTime.now().withHour(11).withMinute(0).withSecond(0).withNano(0);
        // 마감 날짜 시작 날짜 + 7
//        LocalDateTime endDate = startDate.plusDays(7).withHour(11).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = startDate.plusDays(0).withHour(19).withMinute(50).withSecond(0).withNano(0);
        // 발표일 : 마감 날짜 + 1 18:00:00
        LocalDateTime luckDate = endDate.plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
        //상품 등록
        for (LuckyDraw luckyDraw : ready) {
            luckyDraw.changeDate(startDate, endDate, luckDate);
            luckyDraw.changeLuckyProcessStatus(LuckyProcessStatus.PROCESS);
        }



    }



    //관리자 페이지 럭키드로우 상품 다건 조회
    public AdminRespDto.LuckyDrawsRespDto getLuckyDraws(LuckyProcessStatus luckyProcessStatus) {

        List<LuckyDraw> luckyDrawList = luckyDrawRepository.findByLuckyProcessStatus(luckyProcessStatus);

        return new AdminRespDto.LuckyDrawsRespDto(luckyProcessStatus,luckyDrawList);

    }



    //실제 판매중인 상품 대분류별 조회
    public List<ProductRespDto> findProductsByDepartment(String mainDepartment) {

        List<ProductRespDto> 범수야 = productRepository.findProductsByDepartment(mainDepartment);
        return 범수야;


    }

    //사용자가 요청한 상품, 중복시 삭제
    public String deleteRequest(Long productId) {

        Optional<Product> productPs = productRepository.findById(productId);
        productPs.ifPresent(product -> {productRepository.delete(product);});

        String message = productId.toString()+"삭제 완료";

        return message;

    }
}
