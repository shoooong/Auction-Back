package com.example.backend.service.Product;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.dto.product.Detail.RecentlyPriceDto;
import com.example.backend.dto.product.Detail.SalesBiddingDto;
import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Product.ProductRepository;
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
import java.util.List;
import java.util.Optional;
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
            BasicInformationDto basicInformationDto = new BasicInformationDto();
            basicInformationDto.setProductId(product.getProductId());
            basicInformationDto.setProductImg(product.getProductImg());
            basicInformationDto.setProductBrand(product.getProductBrand());
            basicInformationDto.setModelNum(product.getModelNum());
            basicInformationDto.setProductName(product.getProductName());
            basicInformationDto.setOriginalPrice(product.getOriginalPrice());
            basicInformationDto.setProductLike(product.getProductLike());

            log.info("basicInformationDto DTO 변환 : {}", basicInformationDto);

            BasicInformationDto priceValue = productRepository.searchProductPrice(modelNum);
            basicInformationDto.setBuyingBiddingPrice(priceValue.getBuyingBiddingPrice());
            basicInformationDto.setSalesBiddingPrice(priceValue.getSalesBiddingPrice());

            RecentlyPriceDto recentlyContractPrice = selectRecentlyPrice(modelNum);
            log.info("recentlyContractPrice : {}", recentlyContractPrice);
            basicInformationDto.setLatestDate(recentlyContractPrice.getLatestDate());
            basicInformationDto.setLatestPrice(recentlyContractPrice.getLatestPrice());
            basicInformationDto.setPreviousPrice(recentlyContractPrice.getPreviousPrice());
            basicInformationDto.setChangePercentage(recentlyContractPrice.getChangePercentage());
            basicInformationDto.setRecentlyContractDate(recentlyContractPrice.getSalesBiddingTime());
            basicInformationDto.setDifferenceContract(recentlyContractPrice.getDifferenceContract());

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
}