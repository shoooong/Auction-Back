package com.example.backend.controller;

import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Users;
import com.example.backend.service.alarm.AlarmService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
@Log4j2
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(String lastEventId, @AuthenticationPrincipal UserDTO userDTO) {

        return ResponseEntity.ok(alarmService.subscribe(userDTO.getUserId(), lastEventId));
    }

//    @GetMapping(value = "/subscribe", produces = "text/event-stream;")
//    public SseEmitter subscribe(
//            @RequestHeader(value = "Last-Event-Id", required = false, defaultValue = "") String lastEventId,
//            @AuthenticationPrincipal UserDTO userDTO) {
//
//        return alarmService.subscribe(userDTO.getUserId(), lastEventId);
//    }
}
