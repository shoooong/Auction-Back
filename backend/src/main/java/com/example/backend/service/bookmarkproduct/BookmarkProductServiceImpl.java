package com.example.backend.service.bookmarkproduct;

import com.example.backend.entity.BookmarkProduct;
import com.example.backend.entity.Product;
import com.example.backend.entity.Users;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.mypage.BookmarkProductRepository;
import com.example.backend.service.bookmarkproduct.BookmarkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

    @Override
    public boolean isProductSizeAvailable(String modelNum, String size) {
        return productRepository.existsByModelNumAndProductSize(modelNum, size);
    }

    @Override
    @Transactional
    public void saveBookmark(Long userId, String modelNum, String size) {
        if (isProductSizeAvailable(modelNum, size)) {
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
        } else {
            throw new RuntimeException("The specified size is not available for this product.");
        }
    }

    @Override
    public boolean isBookmarkExists(Long userId, Long productId) {
        return bookmarkProductRepository.existsByUser_UserIdAndProduct_ProductId(userId, productId);
    }
}
