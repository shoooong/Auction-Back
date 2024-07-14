package com.example.backend.controller.shop;

import com.example.backend.dto.product.TotalProductDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/shop")
@RestController
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public Slice<TotalProductDto> getTotalProduct(@RequestParam("pageNumber") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10);
        return shopService.getTotalProduct(pageable);
    }

}
