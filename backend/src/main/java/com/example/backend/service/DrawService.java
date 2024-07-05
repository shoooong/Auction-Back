package com.example.backend.service;

import com.example.backend.dto.luckyDraw.DrawDto;
import com.example.backend.dto.mypage.drawHistory.DrawDetailsDto;
import com.example.backend.dto.mypage.drawHistory.DrawHistoryDto;
import com.example.backend.entity.Draw;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.LuckyStatus;
import com.example.backend.repository.LuckyDraw.DrawRepository;
import com.example.backend.repository.LuckyDraw.LuckyDrawRepository;
import com.example.backend.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class DrawService {

    private final DrawRepository drawRepository;
    private final UserRepository userRepository;
    private final LuckyDrawRepository luckyDrawRepository;

    /**
     * 럭키드로우 응모 내역 저장
     */
    public DrawDto saveDraw(Long userId, Long luckyDrawId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LuckyDraw luckyDraw = luckyDrawRepository.findById(luckyDrawId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 럭키드로우입니다."));

        Draw draw = Draw.builder()
                .luckyStatus(LuckyStatus.PROCESS)
                .user(user)
                .luckyDraw(luckyDraw)
                .build();

        DrawDto drawDTO = DrawDto.fromEntity(drawRepository.save(draw));

        return drawDTO;
    }

    /**
     * 마이페이지 응모 내역 조회
     */
    public DrawHistoryDto getDrawHistory(Long userId) {
        Long allCount = drawRepository.countAllByUserId(userId);
        Long processCount = drawRepository.countProcessByUserId(userId);
        Long luckyCount = drawRepository.countLuckyByUserId(userId);

        List<DrawDetailsDto> drawDetailsDto = drawRepository.findDrawDetailsByUserId(userId);

        return DrawHistoryDto.builder()
                .allCount(allCount)
                .processCount(processCount)
                .luckyCount(luckyCount)
                .drawDetails(drawDetailsDto)
                .build();
    }
}
