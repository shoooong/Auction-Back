package com.example.backend.service.bookmarkproduct;

public interface BookmarkProductService {
    boolean isProductSizeAvailable(String modelNum, String size);
    void saveBookmark(Long userId, String modelNum, String size);
    boolean isBookmarkExists(Long userId, Long productId);
}
