package com.example.backend.repository;

import com.example.backend.dto.product.Detail.AveragePriceDto;
import com.example.backend.dto.product.Detail.ProductDetailDto;
import com.example.backend.dto.product.Detail.GroupByBuyingDto;
import com.example.backend.dto.product.Detail.SalesBiddingDto;
import com.example.backend.dto.product.ProductResponseDto;
import com.example.backend.entity.*;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.entity.enumData.SalesStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Product.PhotoReviewRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.service.Product.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Log4j2
public class ProductsTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyingBiddingRepository biddingRepository;
    @Autowired
    private SalesBiddingRepository salesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalesBiddingRepository salesBiddingRepository;
    @Autowired
    private PhotoReviewRepository photoReview;

    @Autowired
    private ProductService productService;
    @Autowired
    private PhotoReviewRepository photoReviewRepository;

    @Test
    @Commit
    @Transactional
    public void insertData() {
        Users user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));

        // 테크 제품 5개 생성
        for (int i = 0; i < 5; i++) {
            Product product = Product.builder()
                    .productImg("Tech Image " + (i + 1))
                    .productBrand("Tech Brand " + (i + 1))
                    .modelNum("TECH-" + (i + 1))
                    .productName("테크 제품 " + (i + 1))
                    .originalPrice(200000L + i * 20000)
                    .productLike(0)
                    .mainDepartment("테크")
                    .subDepartment(null)
                    .productQuantity(1)
                    .productSize("Large")
                    .productStatus(ProductStatus.REGISTERED)
                    .build();

            productRepository.save(product);

            SalesBidding salesBidding = SalesBidding.builder()
                    .salesBiddingTime(LocalDateTime.now().plusDays(15))
                    .salesBiddingPrice(250000L + i * 10000)
                    .salesQuantity(1)
                    .salesStatus(SalesStatus.COMPLETE)
                    .product(product)
                    .user(user)
                    .build();
            salesRepository.save(salesBidding);

            BuyingBidding buyBid = BuyingBidding.builder()
                    .buyingBiddingPrice(230000L + i * 8000)
                    .buyingQuantity(1)
                    .biddingStatus(BiddingStatus.COMPLETE)
                    .product(product)
                    .user(user)
                    .build();
            biddingRepository.save(buyBid);

            PhotoReview photoReview = PhotoReview.builder()
                    .reviewImg("Tech Review Image " + (i + 1))
                    .reviewContent("Review content for Tech " + (i + 1))
                    .reviewLike(0)
                    .user(user)
                    .products(product)
                    .build();
            photoReviewRepository.save(photoReview);
        }
    }

    @Test
    @Transactional
        // 소분류 카테고리 조회
    void filterProductsByCategory() {

        List<ProductResponseDto> products = productService.selectCategoryValue("신발");
        log.info("상품 정보 : {}", products);
    }

    @Test
    @Transactional
        // 모델번호를 통해 해당 기본정보 조회
    void selectModelNum() {
        Optional<Product> products = productRepository.findFirstByModelNum("NIKE-2");
        log.info("상품 정보 : {}", products);

    }
    @Test
    @Transactional
    void recentlyPriceSelect() {
        List<SalesBiddingDto> products = productRepository.recentlyTransaction("NIKE-1");
        log.info("상품 정보 : {}", products);

    }

    @Test
    @Transactional
        // 해당 모델번호가 가지고 있는 구매(최저), 판매(최고) 입찰 희망가격 조회
    void selectModelPrice() {
        ProductDetailDto priceResponseDto = productRepository.searchProductPrice("NIKE-1");
        log.info("상품 정보 : {}", priceResponseDto);
    }

    @Test
    // 해당 productId를 통해 최근 체결가격 가져오기
    public void testFind() {
        Optional<Product> price = productRepository.findFirstByModelNumOrderByLatestDateDesc("NIKE-1");
        assertNotNull(price);
        System.out.println("Latest Sales Bidding: " + price);
    }
    @Test
    public void testFindGroupByBuying() {
        List<GroupByBuyingDto> temp = productRepository.GroupByBuyingInfo("NIKE-1");
        assertNotNull(temp);
    }

    @Test
    public void testFindLatestBiddingDate() {
        List<AveragePriceDto> temp = productRepository.AveragePriceInfo("NIKE-1");
        AveragePriceDto averagePriceDto = temp.get(0);
        log.info("최초에 체결된 거래 날짜 : {}", averagePriceDto.toString());
    }

}
