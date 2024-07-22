package com.example.backend.controller.requesstproduct;

import com.example.backend.dto.product.RequestProductDto;
import com.example.backend.service.requestproduct.RequestProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class RequestProductController {

    @Autowired
    private RequestProductService requestProductService;

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRequestProduct(@RequestBody RequestProductDto requestProductDto) {
        requestProductService.createRequestProduct(requestProductDto);
    }
}
