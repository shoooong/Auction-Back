package com.example.backend.controller;

import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
@Log4j2
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDTO userDTO) {

        return alarmService.subscribe(userDTO.getUserId());
    }
}
