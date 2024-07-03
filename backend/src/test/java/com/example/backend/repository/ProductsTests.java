package com.example.backend.repository;

import com.example.backend.dto.product.ProductResponseDto;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BinddingStatus;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

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
                .productName("나이키 신부 나이르 1")
                .originalPrice(149000L)
                .productLike(0)
                .mainDepartment("의류")
                .subDepartment("신발")
                .productQuantity(7)
                .productSize("270")
                .productStatus(ProductStatus.REQUEST)
                .build();

        productRepository.save(product);

        BuyingBidding buyBid = BuyingBidding.builder()
                .buyingBiddingPrice(124000L)
                .buyingQuantity(1)
                .binddingStatus(BinddingStatus.PROCESS)
                .buyingPrice(129000L)
                .product(product)
                .user(user)
                .build();
        biddingRepository.save(buyBid);

        System.out.println("Products, Sizes, SizePrices, and Bids inserted successfully.");

    }

    @Test
    @Transactional
    void productsSelectAll() {
//        List<Product> productsList = productsRepository.findAll();
//        for (Product product : productsList) {
//            List<Size> sizes = sizeRepository.findByProduct(product);
//            for (Size size : sizes) {
//                List<SizePrice> sizePrices = sizePriceRepository.findBySize(size);
//                List<Bid> bids = bidRepository.findBySize(size);
//
//                log.info("Products Info: {}, || Size: {}, SizePrice: {}, Bid: {}", product, size, sizePrices, bids);
//            }
//        }
    }

    @Test
    @Transactional
    void deleteProduct() {
//        sizePriceRepository.deleteById(11L);
//        log.info("SizePrice deleted successfully.");
//
//        sizeRepository.deleteById(11L);
//        log.info("Size deleted successfully.");
//
//        productsRepository.deleteById(11L);
//        log.info("Product deleted successfully.");
    }

//    @Test
//    @Transactional
//    void updateProductInfo() {
//        productsRepository.updateProducts(13L, "하의");
//        log.info("Product updated successfully.");
//    }

    @Test
    @Transactional
    void filterProductsByCategory() {

        List<ProductResponseDto> products = productService.selectCategoryValue("신발");
        log.info("상품 정보 : {}", products);

    }

    @Test
    @Transactional
    public void testDetailProductInfo() {

//        Product products = productsRepository.findFirstByModelNum("IAB-10");
//
//        log.info("Products Info: {}", products);
    }
}
