package com.example.backend.repository;

import com.example.backend.entity.RequestProduct;
import com.example.backend.entity.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class RequestProductRepositoryTests {

    @Autowired
    private RequestProductRepository requestProductRepository;

    @Autowired
    private UserRepository userRepository;


    //더미데이터로 미등록 요청 상품 등록
    @Test
    public void insertRequestProduct(){

        //test user = sue 생성
        User sue = User.builder()
                .nickname("sue")
                .grade(0)
                .email("sue@gmail.com")
                .password("1234")
                .build();

        userRepository.save(sue);
//        Long sId = sue.getUserId();

        // 현재 날짜를 가져오기
        LocalDate currentDate = LocalDate.now();
        for (int i = 0 ; i <= 100 ; i ++){


            System.out.println("Current Date: " + currentDate);
            RequestProduct requestProduct = RequestProduct.builder()
                    .user(sue)
                    .requestTitle("title" + i)
                    .requestDate(currentDate)
                    .requestProductName("productName"+i)
                    .requestProductSize("size"+i)
                    .requestProductColor("color"+ i)
                    .requestProductImage("image" + i)
                    .approval(false)
                    .requestPrice(BigDecimal.valueOf(10000 + i))
                    .brand("brand"+ i)
                    .openPrice(BigDecimal.valueOf(20000 + i))
                    .build();
            requestProductRepository.save(requestProduct);
        }
    }

    //미등록 요청 상품 조회
//    @Test
//    public void ReadAll(){
//        Optional<RequestProduct> requestProduct = requestProductRepository.findAll();
//
//
//    }
}
