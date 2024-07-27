package com.example.backend.controller;

import com.amazonaws.services.ec2.model.Reservation;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
@Log4j2
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<ResponseBodyEmitter> subscribe(@AuthenticationPrincipal UserDTO userDTO) {

//        return ResponseEntity.ok(alarmService.subscribe(userDTO.getUserId(), lastEventId));
        return ResponseEntity.ok(alarmService.subscribe(userDTO.getUserId()));
    }


}
