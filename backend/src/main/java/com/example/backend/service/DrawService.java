package com.example.backend.service;

import com.example.backend.dto.luckyDraw.DrawDTO;
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


@Service
@RequiredArgsConstructor
@Log4j2
public class DrawService {

    private final DrawRepository drawRepository;
    private final UserRepository userRepository;
    private final LuckyDrawRepository luckyDrawRepository;


    public DrawDTO saveDraw(Long userId, Long luckyDrawId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LuckyDraw luckyDraw = luckyDrawRepository.findById(luckyDrawId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 럭키드로우입니다."));

        Draw draw = Draw.builder()
                .luckyStatus(LuckyStatus.PROCESS)
                .user(user)
                .luckyDraw(luckyDraw)
                .build();

        DrawDTO drawDTO = DrawDTO.fromEntity(drawRepository.save(draw));

        return drawDTO;
    }
}
