package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.enumData.ProductStatus;
import com.example.backend.repository.Product.ProductRepository;
import com.example.backend.service.objectstorage.ObjectStorageService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class AdminTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectStorageService objectStorageService;



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
                        .originalPrice(BigDecimal.valueOf(1000.00 + i * 100))
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
                        .originalPrice(BigDecimal.valueOf(1000 + i * 100))
                        .mainDepartment(mainDepartment)
                        .productQuantity(i * 10)
                        .productSize("사이즈" + i)
                        .productStatus(ProductStatus.REGISTERED)
                        .build();
                productRepository.save(product);
            }
        }
    }

    @Test
    public void insertTestProduct2(){
        Product product = Product.builder()
                    .productImg("Tech Image")
                    .productBrand("Tech Brand ")
                    .modelNum("TECH-")
                    .productName("테크 제품 ")
                    .originalPrice(BigDecimal.valueOf(200000L))
                    .productLike(0)
                    .mainDepartment("테크")
                    .subDepartment(null)
                    .productQuantity(1)
                    .productSize("Large")
                    .productStatus(ProductStatus.REQUEST)
                    .build();
        productRepository.save(product);


    }


    String bucketName = "push";
    String directoryPath = "shooong/luckydraw";

    @Test
    @Transactional
    void createProductWithImage() throws Exception {


        // Mock 이미지 파일 생성
        String filePath = "src/test/resources/test-image.jpg"; // 테스트 이미지 경로
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                Files.readAllBytes(Paths.get(filePath))
        );

        // 이미지 파일을 S3에 업로드
        String imageUrl = objectStorageService.uploadFile(bucketName, directoryPath, mockFile);

        // 더미 데이터 생성
        Product product = Product.builder()
                .productImg(imageUrl)
                .productBrand("Test Brand")
                .productName("Test Product")
                .modelNum(UUID.randomUUID().toString())
                .originalPrice(new BigDecimal("100.00"))
                .productLike(0)
                .mainDepartment("Test Main Department")
                .subDepartment("Test Sub Department")
                .productQuantity(10)
                .productSize("M")
                .productStatus(ProductStatus.REGISTERED)
                .latestPrice(new BigDecimal("100.00"))
                .previousPrice(new BigDecimal("90.00"))
                .previousPercentage(10.0)
                .differenceContract(100L)
                .build();

        // 데이터베이스에 저장
        productRepository.save(product);
    }

}
