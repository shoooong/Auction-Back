package com.example.backend.controller;

import com.example.backend.dto.alarm.AlarmDto;
import com.example.backend.entity.User;
import com.example.backend.service.AlarmServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmServiceImpl alarmService;

//    public ResponseEntity<AlarmDto> request(@AuthenticationPrincipal User user) {
//
//    }
}
