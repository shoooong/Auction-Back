package com.example.backend.controller.shop;

import com.example.backend.dto.product.AllProductDto;
import com.example.backend.service.shop.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SeacrhController {

    private final SearchService searchService;

    @GetMapping
    public Slice<AllProductDto> searchKeyword(@RequestParam("pageNumber") int pageNumber, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, 20);

        return searchService.getSearch(keyword, pageable);
    }
}
