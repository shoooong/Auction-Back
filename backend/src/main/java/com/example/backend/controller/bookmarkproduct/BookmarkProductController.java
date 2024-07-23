package com.example.backend.controller.bookmarkproduct;

import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.bookmarkproduct.BookmarkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkProductController {

    private final BookmarkProductService bookmarkProductService;

    @Autowired
    public BookmarkProductController(BookmarkProductService bookmarkProductService) {
        this.bookmarkProductService = bookmarkProductService;
    }

    @PostMapping
    public ResponseEntity<String> saveBookmark(@AuthenticationPrincipal UserDTO userDTO,
                                               @RequestParam String modelNum,
                                               @RequestParam String productSize) {
        try {
            Long userId = userDTO.getUserId();
            bookmarkProductService.saveBookmark(userId, modelNum, productSize);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bookmark saved successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
