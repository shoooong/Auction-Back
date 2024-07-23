package com.example.backend.dto.admin;

import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductReqDto {

    private String productImg;
    private String productBrand;
    private String modelNum;
    private String productName;
    private BigDecimal originalPrice;
    private String productSize;
    private ProductStatus productStatus;
    //수정용
    private MultipartFile productPhoto;





}
