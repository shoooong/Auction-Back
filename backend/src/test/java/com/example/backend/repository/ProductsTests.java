package com.example.backend.repository;

import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.dto.product.Detail.SalesBiddingDto;
import com.example.backend.dto.product.ProductResponseDto;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import com.example.backend.entity.SalesBidding;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.entity.enumData.SalesStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
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
    private ProductService productService;

    @Test
    @Commit
    @Transactional
    void productsInsertAndSearchTest() {

        Users user = userRepository.findById(3L).orElseThrow(() -> new RuntimeException("User not found"));

        Product product = Product.builder()
                .productImg("Nike Shoes")
                .productBrand("NIKE")
                .modelNum("NIKE-2")
                .productName("나이키 테스트용")
                .originalPrice(149000L)
                .productLike(0)
                .mainDepartment("의류")
                .subDepartment("신발")
                .productQuantity(1)
                .productSize("230")
                .productStatus(ProductStatus.REGISTERED)
                .build();

        productRepository.save(product);

        SalesBidding salesBidding = SalesBidding.builder()
                .salesBiddingTime(LocalDateTime.now().plusDays(15))
                .salesBiddingPrice(172500L)
                .salesQuantity(1)
                .salesStatus(SalesStatus.COMPLETE)
                .salesBiddingPrice(165000L)
                .product(product)
                .user(user)
                .build();
        salesRepository.save(salesBidding);

        BuyingBidding buyBid = BuyingBidding.builder()
                .buyingBiddingPrice(132000L)
                .buyingQuantity(1)
                .biddingStatus(BiddingStatus.COMPLETE)
                .buyingBiddingPrice(139000L)
                .product(product)
                .user(user)
                .build();
        biddingRepository.save(buyBid);

        System.out.println("Products, Sizes, SizePrices, and Bids inserted successfully.");

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
        // 해당 모델번호가 가지고 있는 구매(최저), 판매(최고) 입찰 희망가격 조회
    void selectModelPrice() {
        BasicInformationDto priceResponseDto = productRepository.searchProductPrice("NIKE-2");
        log.info("상품 정보 : {}", priceResponseDto);
    }

    @Test
    // 해당 productId를 통해 최근 체결과 가져오기
    public void testFind() {
        SalesBiddingDto latestBidding = productRepository.RecentlyTransaction(89L);
        assertNotNull(latestBidding);
        System.out.println("Latest Sales Bidding: " + latestBidding);
    }
}
