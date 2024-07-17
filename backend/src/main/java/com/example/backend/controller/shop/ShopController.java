package com.example.backend.controller.shop;

import com.example.backend.dto.product.AllProductDto;
import com.example.backend.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/shop")
@RestController
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/all")
    public Slice<AllProductDto> getTotalProduct(@RequestParam("pageNumber") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10);
        return shopService.getTotalProduct(pageable);
    }

    @GetMapping
    public Slice<AllProductDto> getFilterProducts(@RequestParam("pageNumber") int pageNumber, @RequestParam("subDepartment") String subDepartment) {

        String[] subDepartments = null;
        if (subDepartment.contains(",")) {
            subDepartments = subDepartment.split(",");
        } else {
            subDepartments = new String[]{subDepartment};
        }

        System.out.println("subDepartment: " + subDepartment);

        Pageable pageable = PageRequest.of(pageNumber, 10);
        return shopService.getFilter(pageable, Arrays.asList(subDepartments));
    }

}
