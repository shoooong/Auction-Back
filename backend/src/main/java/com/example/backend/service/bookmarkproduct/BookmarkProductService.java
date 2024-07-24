package com.example.backend.service.bookmarkproduct;

import com.example.backend.dto.bookmarkproduct.BookmarkProductDto;

import java.util.List;

public interface BookmarkProductService {
    void saveBookmark(BookmarkProductDto bookmarkProductDto);

    boolean isProductSizeAvailable(String modelNum, String size);

    boolean isBookmarkExists(Long userId, Long productId);

    List<BookmarkProductDto> getUserBookmarks(Long userId);

    public void deleteBookmark(final long bookmarkProductId, Long userId );
}
