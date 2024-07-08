package com.example.backend.repository;

import com.example.backend.entity.LuckyDraw;
import com.example.backend.repository.LuckyDraw.LuckyDrawRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class LuckyDrawRepositoryTests {

    @Autowired
    private LuckyDrawRepository luckyDrawRepository;

    @Test
    public void testInsertDummies() {

        List<LuckyDraw> luckyDraws = Arrays.asList(
                LuckyDraw.builder()
                        .luckyName("Razer Viper V3 Pro Black(Korean Ver.")
                        .content("레이저 바이퍼 V3 프로 블랙 (국내 정식 발매 제품)")
                        .luckyImage("aaa")
                        .luckyStartDate(LocalDateTime.of(2024,6,20,11,0))
                        .luckyEndDate(LocalDateTime.of(2024,6,30,11,0))
                        .luckyDate(LocalDateTime.of(2024,7,1,18,0))
                        .luckyPeople(3)
                        .build(),
                LuckyDraw.builder()
                        .luckyName("Swatch x Omega x Snoopy Bioceramic Moonswatch Mission To The Moonphase Full Moon")
                        .content("스와치 x 오메가 x 스누피 바이오세라믹 문스와치 미션 투 더 문페이스 풀 문")
                        .luckyImage("aaa")
                        .luckyStartDate(LocalDateTime.of(2024,6,22,11,0))
                        .luckyEndDate(LocalDateTime.of(2024,7,2,11,0))
                        .luckyDate(LocalDateTime.of(2024,7,3,18,0))
                        .luckyPeople(1)
                        .build(),
                LuckyDraw.builder()
                        .luckyName("Hermes Large Bride-A-Brac Case Canvas & Naturel")
                        .content("에르메스 라지 브리드 어 브랙 케이스 캔버스 & 나뛰렐")
                        .luckyImage("aaa")
                        .luckyStartDate(LocalDateTime.of(2024,6,28,11,0))
                        .luckyEndDate(LocalDateTime.of(2024,7,5,11,0))
                        .luckyDate(LocalDateTime.of(2024,7,6,18,0))
                        .luckyPeople(1)
                        .build()
        );

        luckyDrawRepository.saveAll(luckyDraws);
    }

    @Test
    public void testInsertProduct() {
        LuckyDraw luckyDraw = LuckyDraw.builder()
                .luckyName("Razer Viper V3 Pro Black(Korean Ver.")
                .content("레이저 바이퍼 V3 프로 블랙 (국내 정식 발매 제품)")
                .luckyImage("aaa")
                .luckyStartDate(LocalDateTime.of(2024,6,20,11,0))
                .luckyEndDate(LocalDateTime.of(2024,6,30,11,0))
                .luckyDate(LocalDateTime.of(2024,7,1,18,0))
                .luckyPeople(3)
                .build();

        luckyDrawRepository.save(luckyDraw);
    }

    @Test
    public void findAllProducts() {
        List<LuckyDraw> luckyDraws = luckyDrawRepository.findAll();

        luckyDraws.forEach(luckyDraw -> {
            log.info(luckyDraw.toString());
        });
    }

    @Test
    public void findProductById() {
        for (int i=1; i<=luckyDrawRepository.count(); i++){
            Optional<LuckyDraw> luckyDraw = luckyDrawRepository.findById((long) i);

            log.info(luckyDraw.toString());
        }
    }

    @Test
    public void testDeleteProduct() {
        Long luckyId = 1L;

        luckyDrawRepository.deleteById(luckyId);
    }

}
