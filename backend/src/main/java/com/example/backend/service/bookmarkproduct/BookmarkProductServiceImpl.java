package com.example.backend.service.bookmarkproduct;

import com.example.backend.dto.bookmarkproduct.BookmarkProductDto;
import com.example.backend.entity.BookmarkProduct;
import com.example.backend.entity.Product;
import com.example.backend.entity.Users;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.mypage.BookmarkProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BookmarkProductServiceImpl implements BookmarkProductService {

    private final ProductRepository productRepository;
    private final BookmarkProductRepository bookmarkProductRepository;
    private final UserRepository usersRepository;

    @Autowired
    public BookmarkProductServiceImpl(ProductRepository productRepository,
                                      BookmarkProductRepository bookmarkProductRepository,
                                      UserRepository usersRepository) {
        this.productRepository = productRepository;
        this.bookmarkProductRepository = bookmarkProductRepository;
        this.usersRepository = usersRepository;
    }

    @Autowired
    private SalesBiddingRepository salesBiddingRepository;

    // 관심상품 저장
    @Override
    @Transactional
    public void saveBookmark(BookmarkProductDto bookmarkProductDto) {
        String modelNum = bookmarkProductDto.getModelNum();
        String size = bookmarkProductDto.getProductSize();
        Long userId = bookmarkProductDto.getUserId();

        if (!isProductSizeAvailable(modelNum, size)) {
            throw new RuntimeException("The specified size is not available for this product.");
        }

        Product product = productRepository.findByModelNumAndProductSize(modelNum, size)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (isBookmarkExists(userId, product.getProductId())) {
            throw new RuntimeException("Bookmark already exists");
        }

        BookmarkProduct bookmarkProduct = BookmarkProduct.builder()
                .user(user)
                .product(product)
                .build();
        bookmarkProductRepository.save(bookmarkProduct);
    }

    // 해당 사이즈가 있는지 확인
    public boolean isProductSizeAvailable(String modelNum, String size) {
        return productRepository.existsByModelNumAndProductSize(modelNum, size);
    }

    // 해당 상품이 이미 북마크에 저장되어있는지 확인
    public boolean isBookmarkExists(Long userId, Long productId) {
        return bookmarkProductRepository.existsByUser_UserIdAndProduct_ProductId(userId, productId);
    }

    // 관심상품 조회
    @Override
    public List<BookmarkProductDto> getUserBookmarks(Long userId) {
        List<BookmarkProduct> bookmarkProducts = bookmarkProductRepository.findByUser_UserId(userId);
        log.info("Found {} bookmark products {}", bookmarkProducts.size(), userId);

        return bookmarkProducts.stream()
                .map(bookmarkProduct -> {
                    BookmarkProductDto bookmarkProductDto = new BookmarkProductDto();
                    bookmarkProductDto.setUserId(bookmarkProduct.getUser().getUserId());
                    bookmarkProductDto.setProductId(bookmarkProduct.getProduct().getProductId());
                    bookmarkProductDto.setProductImg(bookmarkProduct.getProduct().getProductImg());
                    bookmarkProductDto.setModelNum(bookmarkProduct.getProduct().getModelNum());
                    bookmarkProductDto.setProductSize(bookmarkProduct.getProduct().getProductSize());
                    bookmarkProductDto.setProductBrand(bookmarkProduct.getProduct().getProductBrand());
                    bookmarkProductDto.setProductName(bookmarkProduct.getProduct().getProductName());

                    // 가장 낮은 판매 입찰가 조회 및 설정
                    BigDecimal lowestPrice = salesBiddingRepository.findLowestSalesBiddingPriceByProductId(bookmarkProduct.getProduct().getProductId());
                    bookmarkProductDto.setSalesBiddingPrice(lowestPrice);

                    return bookmarkProductDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookmark(final long bookmarkProductId, Long userId ) {

        BookmarkProduct bookmarkProduct = bookmarkProductRepository.findById(bookmarkProductId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));

        if(!bookmarkProduct.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to delete bookmark");
        }
        bookmarkProductRepository.delete(bookmarkProduct);
    }
}