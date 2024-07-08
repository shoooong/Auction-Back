package com.example.backend.controller;

import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.dto.luckyDraw.DrawDto;
import com.example.backend.dto.luckyDraw.LuckyDrawsDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.service.DrawService;
import com.example.backend.service.LuckyDrawService;
import com.example.backend.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("luckydraw")
@Log4j2
public class LuckyDrawController {

    private final LuckyDrawService luckyDrawService;
    private final DrawService drawService;

    private final AlarmService alarmService;

    /**
     * 럭키드로우 메인
     */
    @GetMapping("")
    public List<LuckyDrawsDto> luckyDraws() {
        return luckyDrawService.getAllLuckyDraws();
    }

    /**
     * 럭키드로우 상세
     */
    @GetMapping("/{luckyId}")
    public LuckyDrawsDto luckyDrawById(@PathVariable("luckyId") Long luckyId) {
        return luckyDrawService.getLuckyDrawById(luckyId);
    }


    /**
     * 사용자 럭키드로우 응모
     */
    @PostMapping("/{luckyId}/enter")
    public ResponseEntity<DrawDto> enterLuckyDraw(@PathVariable Long luckyId, @AuthenticationPrincipal UserDTO userDTO) {

        Long userId = userDTO.getUserId();

        DrawDto drawDTO = drawService.saveDraw(userId, luckyId);

        // 알림 전송
        alarmService.saveAlarm(userId, AlarmType.LUCKYAPPLY);

        return ResponseEntity.ok().body(drawDTO);
    }

}