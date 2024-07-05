package com.example.backend.service;

import com.example.backend.dto.luckyDraw.LuckyDrawsDto;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.enumData.LuckyStatus;
import com.example.backend.repository.LuckyDraw.DrawRepository;
import com.example.backend.repository.LuckyDraw.LuckyDrawRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LuckyDrawService {

    private final LuckyDrawRepository luckyDrawRepository;
    private final DrawRepository drawRepository;

    /**
     * 럭키드로우 메인페이지 상품 전체 조회
     */
    public List<LuckyDrawsDto> getAllLuckyDraws(){
        return luckyDrawRepository.findAll().stream()
                .map(LuckyDrawsDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 럭키드로우 상세페이지 상품 정보 조회
     */
    public LuckyDrawsDto getLuckyDrawById(Long luckyId){
        return luckyDrawRepository.findById(luckyId)
                .map(LuckyDrawsDto::fromEntity)
                .orElseThrow(()-> new IllegalArgumentException("Lucky Draw Not Found: " + luckyId));
    }

    /**
     * 매일 18시에 luckyDate 확인 후 luckyStatus 변경
     */
    @Scheduled(cron = "0 0 18 * * *")
    @Transactional
    public void getTodayLucky(){

        List<LuckyDraw> luckyDrawList =  luckyDrawRepository.findTodayLucky(LocalDateTime.now());

        List<LuckyDrawsDto> luckyDrawsDto = luckyDrawList.stream()
                .map(LuckyDrawsDto::fromEntity)
                .toList();

        luckyDrawsDto.stream()
                .map(LuckyDrawsDto::getLuckyId)
                .forEach(this::changeLuckyStatus);
    }

    @Transactional
    public void changeLuckyStatus(Long luckyId) {
        LuckyDraw luckyDraw = validateLuckyId(luckyId);

        List<Long> drawIdList = drawRepository.findAllDrawIdByLuckyDraw(luckyId);

        if (!drawIdList.isEmpty()) {
            Random random = new Random();

            // 당첨인원 기준 랜덤으로 당첨자의 drawId 선정
            List<Long> pickDrawIdList = random.ints(0, drawIdList.size())
                    .distinct()
                    .limit(luckyDraw.getLuckyPeople())
                    .mapToObj(drawIdList::get)
                    .collect(Collectors.toList());

            drawRepository.updateLuckyStatus(LuckyStatus.LUCKY, pickDrawIdList);

            luckyDrawRepository.updateEndStatus(luckyId);

            drawIdList.removeAll(pickDrawIdList);
            drawRepository.updateLuckyStatus(LuckyStatus.UNLUCKY, drawIdList);
        }
    }

    /**
     * 응모마감일(luckyEndDate) 지나면 메인페이지에서 내리기
     */

    /**
     * 존재하는 럭키드로우인지 검사
     */
    public LuckyDraw validateLuckyId(Long luckyId) {
        return luckyDrawRepository.findById(luckyId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 럭키드로우 입니다."));
    }

}

