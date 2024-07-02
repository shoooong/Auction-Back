package com.example.backend.controller;


import com.example.backend.service.AlarmServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmServiceImpl alarmService;

//    public ResponseEntity<AlarmDto> request(@AuthenticationPrincipal User user) {
//
//    }
}
