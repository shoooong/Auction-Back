package com.example.backend.controller;

import com.example.backend.dto.alarm.AlarmDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Alarm;
import com.example.backend.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
@Log4j2
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/request")
    public ResponseEntity<?> request(@AuthenticationPrincipal UserDTO userDTO) {
        List<?> list = alarmService.getAllAlarmList(userDTO.getUserId());

        return ResponseEntity.ok(list);
    }
}
