package com.example.backend.service.Product;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.*;
import com.example.backend.entity.PhotoReview;
import com.example.backend.entity.Product;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Product.PhotoReviewRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final BuyingBiddingRepository buyingBiddingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private LocalDateTime lastCheckedTime;
    private boolean isUpdated = false;
    @Autowired
    private PhotoReviewRepository photoReviewRepository;
    @Autowired
    private UserRepository userRepository;

    // 상품 소분류 조회
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> selectCategoryValue(String subDepartment) {
        log.info("subDepartment : {}", subDepartment);

        List<Product> subProduct = productRepository.subProductInfo(subDepartment);
        log.info("subProduct : {}", subProduct);

        return subProduct.stream()
                .map(this::convertProductDto)
                .collect(Collectors.toList());
    }

    private ProductResponseDto convertProductDto(Product product) {
        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductImg(product.getProductImg());
        productDto.setProductBrand(product.getProductBrand());
        productDto.setProductName(product.getProductName());
        productDto.setProductLike(product.getProductLike());
        productDto.setModelNum(product.getModelNum());

        // Product 와 연관된 BuyingBidding 엔티티들을 BuyingDto로 변환
        List<BuyingDto> buyingDtoList = buyingBiddingRepository.findByProductAndBiddingStatus(product, BiddingStatus.PROCESS).stream()
                .map(buyingBidding -> {
                    BuyingDto buyingDto = new BuyingDto();
                    buyingDto.setBuyingId(buyingBidding.getBuyingBiddingId());
                    buyingDto.setBuyingBiddingTime(buyingBidding.getBuyingBiddingTime());
                    buyingDto.setBuyingBiddingPrice(buyingBidding.getBuyingBiddingPrice());
                    return buyingDto;
                })
                .collect(Collectors.toList());

        // 최저 입찰가 찾기
        Long minPrice = buyingDtoList.stream()
                .mapToLong(BuyingDto::getBuyingBiddingPrice)
                .min()
                .orElse(0L);

        // 변환된 BuyingDto 리스트와 최저 입찰가를 ProductResponseDto 에 설정
        productDto.setBuyingDto(buyingDtoList);
        productDto.setProductMinPrice(minPrice);

        return productDto;
    }

    // 상품의 기본정보 조회
    @Override
    @Transactional
    public BasicInformationDto basicInformation(String modelNum) {
        log.info("modelNum : {}", modelNum);

        Optional<Product> productOptional = productRepository.findFirstByModelNum(modelNum);
        // 망할 ModelMapper 이슈로
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            BasicInformationDto priceValue = productRepository.searchProductPrice(modelNum);

            List<ProductsContractListDto> contractInfoList = selectSalesContract(modelNum);

            List<SalesHopeDto> salesHopeDtoList = selectSalesHope(modelNum);

            List<BuyingHopeDto> buyingHopeDtoList = selectBuyingHope(modelNum);

            List<PhotoReviewDto> photoReviewDtoList = selectPhotoReview(modelNum);


            RecentlyPriceDto recentlyContractPrice = selectRecentlyPrice(modelNum);
            BasicInformationDto basicInformationDto = BasicInformationDto.builder()
                    .productId(product.getProductId())
                    .productImg(product.getProductImg())
                    .productBrand(product.getProductBrand())
                    .modelNum(product.getModelNum())
                    .productName(product.getProductName())
                    .originalPrice(product.getOriginalPrice())
                    .productLike(product.getProductLike())

                    .buyingBiddingPrice(priceValue.getBuyingBiddingPrice())
                    .salesBiddingPrice(priceValue.getSalesBiddingPrice())

                    .latestDate(recentlyContractPrice.getLatestDate())
                    .latestPrice(recentlyContractPrice.getLatestPrice())
                    .previousPrice(recentlyContractPrice.getPreviousPrice())
                    .changePercentage(recentlyContractPrice.getChangePercentage())
                    .recentlyContractDate(recentlyContractPrice.getSalesBiddingTime())
                    .differenceContract(recentlyContractPrice.getDifferenceContract())

                    .contractInfoList(contractInfoList)
                    .salesHopeList(salesHopeDtoList)
                    .buyingHopeList(buyingHopeDtoList)

                    .photoReviewList(photoReviewDtoList)

                    .build();

            log.info("상세상품 변환 완료 : {}", basicInformationDto);
            return basicInformationDto;
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateDate(Long recentlyProductId) {
        if (isUpdated) {
            Optional<Product> optionalProduct = productRepository.findProductsByProductId(recentlyProductId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.updateLatestDate(LocalDateTime.now());
                productRepository.save(product);
                log.info("서버 종료 시점 저장 완료 : {}", LocalDateTime.now());
                productRepository.flush(); // 강제로 flush
                entityManager.clear();     // 엔티티 매니저 캐시 비우기
            }
        }
    }

    // 최근 체결가 계산
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RecentlyPriceDto selectRecentlyPrice(String modelNum) {
        Optional<Product> oldContractValue = productRepository.findFirstByModelNumOrderByLatestDateDesc(modelNum);
        if (oldContractValue.isPresent()) {
            lastCheckedTime = oldContractValue.get().getLatestDate();
        } else {
            lastCheckedTime = LocalDateTime.now();
        }
        log.info("!!! 서버가 마지막까지 유지했던 시간 : {}", lastCheckedTime);

        List<SalesBiddingDto> newAllContractSelect = productRepository.recentlyTransaction(modelNum);
        log.info("최근 체결 내역 조회 : {} ", newAllContractSelect);
        if (newAllContractSelect.isEmpty()) {
            log.info("체결된 거래가 없습니다.");
            return new RecentlyPriceDto();
        }

        SalesBiddingDto recentlyContractValue = newAllContractSelect.get(0);
        LocalDateTime recentlyContractTime = recentlyContractValue.getSalesBiddingTime();
        log.info("최근 체결 내역 시간 : {}", recentlyContractTime);

        RecentlyPriceDto recentlyPriceDto = new RecentlyPriceDto();

        recentlyPriceDto.setLatestDate(recentlyContractTime);
        recentlyPriceDto.setLatestPrice(recentlyContractValue.getLatestPrice());
        recentlyPriceDto.setSalesBiddingTime(recentlyContractTime);
        recentlyPriceDto.setSalesBiddingPrice(recentlyContractValue.getSalesBiddingPrice());

        if (lastCheckedTime.isBefore(recentlyContractTime)) {
            for (SalesBiddingDto product : newAllContractSelect) {
                if (product.getPreviousPrice() == null || product.getPreviousPercentage() == null) {
                    productRepository.resetPreviousPrice(product.getProductId());
                    log.info("기본값 설정 완료");
                }
            }

            Long recentlyProductId = recentlyContractValue.getProductId();
            Long recentlyContractPrice = recentlyContractValue.getLatestPrice();
            Long previousContractPrice = oldContractValue.get().getLatestPrice();

            log.info("업데이트 전 recentlyProductId : {}, recentlyContractPrice : {}", recentlyProductId, recentlyContractPrice);

            if (previousContractPrice != null) {
                productRepository.updatePreviousPrice(recentlyProductId, previousContractPrice);
                log.info("Updated previousPrice for productId: {} with price: {}", recentlyProductId, previousContractPrice);
            } else {
                log.warn("previousContractPrice is null, skipping update for previousPrice");
            }
            productRepository.updateLatestPrice(recentlyProductId, recentlyContractPrice);
            log.info("Updated latestPrice for productId: {}", recentlyProductId);
            productRepository.flush();
            entityManager.clear(); // 엔티티 매니저 캐시 비우기

            Long result = recentlyContractPrice - previousContractPrice;
            double changePercentage = (((recentlyContractPrice - previousContractPrice) / (double) previousContractPrice) * 100);
            DecimalFormat df = new DecimalFormat("#.#");
            String format = df.format(changePercentage);
            double finalChangePercentage = Double.parseDouble(format);
            productRepository.updateRecentlyContractPercentage(recentlyProductId, finalChangePercentage);
            productRepository.updateDifferenceContract(recentlyProductId, result);
            recentlyPriceDto.setDifferenceContract(result);
            recentlyPriceDto.setChangePercentage(finalChangePercentage);
            recentlyPriceDto.setPreviousPrice(previousContractPrice);

            lastCheckedTime = recentlyContractTime;
            isUpdated = true;
            log.info("최근 체결 내역 업데이트 완료");
            updateDate(recentlyProductId);
        } else {
            recentlyPriceDto.setLatestDate(oldContractValue.get().getLatestDate());
            recentlyPriceDto.setLatestPrice(oldContractValue.get().getLatestPrice());
            recentlyPriceDto.setDifferenceContract(oldContractValue.get().getDifferenceContract());
            recentlyPriceDto.setPreviousPrice(oldContractValue.get().getPreviousPrice());
            recentlyPriceDto.setChangePercentage(oldContractValue.get().getPreviousPercentage());
            recentlyPriceDto.setSalesBiddingTime(oldContractValue.get().getLatestDate());
            recentlyPriceDto.setSalesBiddingPrice(oldContractValue.get().getLatestPrice());
            log.info("현재 등록된 거래가 최신입니다.");
        }
        return recentlyPriceDto;
    }

    // 체결 내역 관리(리스트)
    @Override
    public List<ProductsContractListDto> selectSalesContract(String modelNum) {

        List<SalesBiddingDto> temp = productRepository.recentlyTransaction(modelNum);

        return temp.stream()
                .map(contractValue -> ProductsContractListDto.builder()
                        .productSize(contractValue.getProductSize())
                        .productContractPrice(contractValue.getLatestPrice())
                        .productContractDate(contractValue.getSalesBiddingTime())
                        .build())
                .collect(Collectors.toList());
    }

    // 입찰 판매 희망 내역(리스트)
    @Override
    public List<SalesHopeDto> selectSalesHope(String modelNum) {
        List<SalesHopeDto> temp = productRepository.SalesHopeInfo(modelNum);

        // LinkedHashMap 사용하여 순서 보장
        Map<String, SalesHopeDto> groupedResults = new LinkedHashMap<>();

        for (SalesHopeDto hope : temp) {
            // 동일한 사이즈와 가격을 기준으로 키 생성
            String key = hope.getProductSize() + "-" + hope.getSalesBiddingPrice();

            // 이미 존재하는 항목이면 수량 증가, 아니면 새로 추가
            if (groupedResults.containsKey(key)) {
                SalesHopeDto existing = groupedResults.get(key);
                existing.setSalesQuantity(existing.getSalesQuantity() + hope.getSalesQuantity());
            } else {
                groupedResults.put(key, hope);
            }
        }
        // 결과를 다시 리스트로 변환
        List<SalesHopeDto> resultList = new ArrayList<>(groupedResults.values());

        log.info("값 리스트로 변환 확인 : {}, ", resultList);
        return resultList;
    }

    // 입찰 구매 희망 내역(리스트)
    @Override
    public List<BuyingHopeDto> selectBuyingHope(String modelNum) {
        List<BuyingHopeDto> temp = productRepository.BuyingHopeInfo(modelNum);

        log.info("구매 입찰 내역 확인 : {} ", temp);
        Map<String, BuyingHopeDto> groupedResults = new LinkedHashMap<>();

        for (BuyingHopeDto hope : temp) {
            // 동일한 사이즈와 가격을 기준으로 키 생성
            String key = hope.getProductSize() + "-" + hope.getBuyingBiddingPrice();

            // 이미 존재하는 항목이면 수량 증가, 아니면 새로 추가
            if (groupedResults.containsKey(key)) {
                BuyingHopeDto existing = groupedResults.get(key);
                existing.setBuyingQuantity(existing.getBuyingQuantity() + hope.getBuyingQuantity());
            } else {
                groupedResults.put(key, hope);
            }
        }
        // 결과를 다시 리스트로 변환
        List<BuyingHopeDto> resultList = new ArrayList<>(groupedResults.values());

        log.info("리스트로 변환 : {}, ", resultList);

        return resultList;
    }

    @Transactional
    public void addPhotoReview(PhotoRequestDto photoRequestDto) {
        Product product = productRepository.findFirstByModelNum(photoRequestDto.getModelNum())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + photoRequestDto.getModelNum()));

        Users user = userRepository.findById(photoRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + photoRequestDto.getUserId()));

        PhotoReview photoReview = PhotoReview.builder()
                .products(product)
                .user(user)
                .reviewLike(0)
                .reviewImg(photoRequestDto.getReviewImg())
                .reviewContent(photoRequestDto.getReviewContent())
                .build();

        photoReviewRepository.save(photoReview);
        log.info("성공!!");
    }

    @Transactional
    public void updatePhotoReview(PhotoRequestDto photoRequestDto) {
        PhotoReview review = photoReviewRepository.findById(photoRequestDto.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + photoRequestDto.getReviewId()));

        Product product = productRepository.findFirstByModelNum(photoRequestDto.getModelNum())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + photoRequestDto.getModelNum()));

        Users user = userRepository.findById(photoRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + photoRequestDto.getUserId()));

        if(!review.getUser().getUserId().equals(photoRequestDto.getUserId())) {
            throw new IllegalArgumentException("해당 리뷰를 수정할 권한이 없습니다.");
        }
        // 리뷰 수정
        PhotoReview photoReview = PhotoReview.builder()
                .products(product)
                .user(user)
                .reviewId(review.getReviewId())
                .reviewLike(0)
                .reviewImg(photoRequestDto.getReviewImg())
                .reviewContent(photoRequestDto.getReviewContent())
                .build();

        photoReviewRepository.save(photoReview);
        log.info("리뷰가 성공적으로 수정되었습니다.");

        log.info("해당 리뷰를 찾을 수 없습니다.");
    }

    @Override
    public void deletePhotoReview(Long reviewId, Long userId) {
        PhotoReview photoReview = photoReviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if(!photoReview.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 리뷰를 삭제할 권한이 없습니다.");
        }
            photoReviewRepository.delete(photoReview);
    }

    // 해당 상품에 대한 스타일 리뷰 조회(리스트)
    @Override
    public List<PhotoReviewDto> selectPhotoReview(String modelNum) {
        List<PhotoReview> photoReviewList = photoReviewRepository.findProductStyleReview(modelNum);

        return photoReviewList.stream()
                .map(productStyleReview -> PhotoReviewDto.builder()
                        .userId(productStyleReview.getUser().getUserId())
                        .reviewImg(productStyleReview.getReviewImg())
                        .reviewContent(productStyleReview.getReviewContent())
                        .reviewLike(productStyleReview.getReviewLike())
                        .build())
                .collect(Collectors.toList());
    }


//    @Transactional
//    public List<PhotoReviewDto> (String modelNum, Long userId) {
//        Product product = productRepository.findFirstByModelNum(modelNum)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + modelNum));
//
//        Users user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
//
//        PhotoReview photoReview = PhotoReview.builder()
//                .user(user)
//                .reviewImg(photoReviewDto.getReviewImg())
//                .reviewContent(photoReviewDto.getReviewContent())
//                .reviewLike(photoReviewDto.getReviewLike())
//                .products(product)
//                .build();
//
//        photoReviewRepository.save(photoReview);
//    }

//    @Override
//    public List<ContractInfoDto> selectContractValue(String modelNum) {
//        List<Product> productList = productRepository.findProductByModelNumOrderByLatestDateDesc(modelNum);
//
//        return productList.stream()
//                .map(product -> ContractInfoDto.builder()
//                        .productSize(product.getProductSize())
//                        .contractPrice(product.getLatestPrice())
//                        .contractDate(product.getLatestDate().toLocalDate())
//                        .build())
//                .collect(Collectors.toList());
//    }

//    public AveragePriceResponseDto getAveragePrices(String modelNum) {
//        LocalDateTime now = LocalDateTime.of(2024, 7, 9, 0, 1);
//        AveragePriceResponseDto responseDto = new AveragePriceResponseDto();
//
//        responseDto.setOneDayPrices(getPrices(modelNum, now.minusDays(1), now, 2));
//        responseDto.setThreeDayPrices(getPrices(modelNum, now.minusDays(3), now, 6));
//        responseDto.setSevenDayPrices(getPrices(modelNum, now.minusDays(7), now, 12));
//        responseDto.setFifteenDayPrices(getPrices(modelNum, now.minusDays(15), now, 24));
//        responseDto.setThirtyDayPrices(getPrices(modelNum, now.minusDays(30), now, 48));
//
//        return responseDto;
//    }
//
//    private List<AveragePriceDto> getPrices(String modelNum, LocalDateTime startDate, LocalDateTime endDate, int intervalHours) {
//        List<AveragePriceDto> averagePrices = new ArrayList<>();
//        LocalDateTime current = startDate;
//
//        while (current.isBefore(endDate)) {
//            LocalDateTime next = current.plusHours(intervalHours);
//            List<Tuple> tuples = productRepository.findHourlyAveragePricesByModelNumAndDateRange(modelNum, current, next);
//
//            for (Tuple tuple : tuples) {
//                String dateTime = tuple.get(0, String.class);
//                Double averagePrice = tuple.get(1, Double.class);
//                averagePrices.add(new AveragePriceDto(dateTime, averagePrice));
//            }
//            current = next;
//        }
//
//        return averagePrices;
//    }
}