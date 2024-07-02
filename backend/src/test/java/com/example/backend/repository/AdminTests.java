package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.repository.Product.ProductRepository;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class AdminTests {

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void insertTestProduct() {
        createTestProducts("의류", new String[]{"상의", "하의", "아우터", "신발", "이너웨어"});
        createTestProducts("라이프", new String[]{"인테리어", "키친", "뷰티"});
        createTestProducts("테크", new String[]{});
    }

    private void createTestProducts(String mainDepartment, String[] subDepartments) {
        for (String subDepartment : subDepartments) {
            for (int i = 1; i <= 10; i++) {
                Product product = Product.builder()
                        .productImg("상품이미지" + i)
                        .productBrand("브랜드" + i)
                        .productName(mainDepartment + " 상품" + i)
                        .modelNum("modelnum" + i)
                        .originalPrice(1000L + i * 100)
                        .mainDepartment(mainDepartment)
                        .subDepartment(subDepartment)
                        .productQuantity(i * 10)
                        .productSize("사이즈" + i)
                        .productStatus(ProductStatus.REGISTERED)
                        .build();
                productRepository.save(product);
            }
        }

        // 소분류가 없는 경우 처리
        if (subDepartments.length == 0) {
            for (int i = 1; i <= 10; i++) {
                Product product = Product.builder()
                        .productImg("상품이미지" + i)
                        .productBrand("브랜드" + i)
                        .productName(mainDepartment + " 상품" + i)
                        .modelNum("modelnum" + i)
                        .originalPrice(1000L + i * 100)
                        .mainDepartment(mainDepartment)
                        .productQuantity(i * 10)
                        .productSize("사이즈" + i)
                        .productStatus(ProductStatus.REGISTERED)
                        .build();
                productRepository.save(product);
            }
        }
    }

}
