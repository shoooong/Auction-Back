package com.example.backend.controller;

import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
@Log4j2
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(){
        return alarmService.subscribe();
    }

    @GetMapping("/send")
    public ResponseEntity<List<ResponseAlarmDto>> sendAlarm(@AuthenticationPrincipal UserDTO userDTO) throws IOException {
        List<ResponseAlarmDto> list = alarmService.getAllAlarmList(userDTO.getUserId());

        return ResponseEntity.ok(list);
    }
}
