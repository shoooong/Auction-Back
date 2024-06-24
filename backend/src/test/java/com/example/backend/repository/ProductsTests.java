package com.example.backend.repository;

import com.example.backend.entity.*;
import com.example.backend.repository.Bid.BidRepository;
import com.example.backend.repository.Category.CategoryRepository;
import com.example.backend.repository.Product.ProductsRepository;
import com.example.backend.repository.Size.SizeRepository;
import com.example.backend.repository.Size.SizePriceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Log4j2
public class ProductsTests {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizePriceRepository sizePriceRepository;

    @Autowired
    private BidRepository bidRepository;

    @Test
    @Commit
    @Transactional
    void productsInsertAndSearchTest() {

            Category category = Category.builder()
                    .categoryType("의류")
                    .categoryName("상의")
                    .build();
            category = categoryRepository.save(category);

            Products product = Products.builder()
                    .productPhoto("Photo test")
                    .productBrand("폴로")
                    .productName("고급진 향기가 나는 향수")
                    .modelNum("smell_good-02")
                    .originalPrice(new BigDecimal("79000"))
                    .productLike(0)
                    .category(category)
                    .build();
            product = productsRepository.save(product);

            Size size = Size.builder()
                    .product(product)
                    .productSize("M")
                    .build();
            size = sizeRepository.save(size);

            SizePrice sizePrice = SizePrice.builder()
                    .size(size)
                    .sellPrice(new BigDecimal("88000"))
                    .quantity(100)
                    .build();
            sizePrice = sizePriceRepository.save(sizePrice);

            Bid bid = Bid.builder()
                    .size(size)
                    .bidKind(Bid.BidKind.BUY)
                    .bidPrice(67500)
                    .bidStartDate(LocalDateTime.now().plusDays(1))
                    .bidModifyDate(LocalDateTime.now().plusDays(2))
                    .bidEndDate(LocalDateTime.now().plusDays(6))
                    .bidStatus(Bid.BidStatus.PROGRESS)
                    .build();
            bid = bidRepository.save(bid);

            System.out.println("Products, Sizes, SizePrices, and Bids inserted successfully.");

    }

    @Test
    @Transactional
    void productsSelectAll() {
        List<Products> productsList = productsRepository.findAll();
        for (Products product : productsList) {
            List<Size> sizes = sizeRepository.findByProduct(product);
            for (Size size : sizes) {
                List<SizePrice> sizePrices = sizePriceRepository.findBySize(size);
                List<Bid> bids = bidRepository.findBySize(size);

                log.info("Products Info: {}, || Size: {}, SizePrice: {}, Bid: {}", product, size, sizePrices, bids);
            }
        }
    }

    @Test
    @Transactional
    void deleteProduct() {
        sizePriceRepository.deleteById(11L);
        log.info("SizePrice deleted successfully.");

        sizeRepository.deleteById(11L);
        log.info("Size deleted successfully.");

        productsRepository.deleteById(11L);
        log.info("Product deleted successfully.");
    }

    @Test
    @Transactional
    void updateProductInfo() {
        productsRepository.updateProducts(13L, "하의");
        log.info("Product updated successfully.");
    }

    @Test
    @Transactional
    void filterProductsByCategory() {
        List<Products> products = productsRepository.selectProductInfo("하의");
        for (Products product : products) {
            log.info("Category '패션' Product Info: {}", product);
            List<Size> sizes = sizeRepository.findByProduct(product);
            for (Size size : sizes) {
                List<SizePrice> sizePrices = sizePriceRepository.findBySize(size);
                List<Bid> bids = bidRepository.findBySize(size);

                log.info("Products Info: {}, || Size: {}, SizePrice: {}, Bid: {}", product, size, sizePrices, bids);
            }
        }
    }
}
