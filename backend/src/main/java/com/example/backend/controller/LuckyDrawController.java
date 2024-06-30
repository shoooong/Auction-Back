package com.example.backend.controller;

import com.example.backend.dto.luckyDraw.LuckyDrawsDTO;
import com.example.backend.service.LuckyDrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("luckydraw")
@Log4j2
public class LuckyDrawController {

    private final LuckyDrawService luckyDrawService;

    @GetMapping("")
    public List<LuckyDrawsDTO> luckyDraws() {
        return luckyDrawService.getAllLuckyDraws();
    }

    @GetMapping("/{luckyId}")
    public LuckyDrawsDTO luckyDrawById(@PathVariable("luckyId") Long luckyId) {
        return luckyDrawService.getLuckyDrawById(luckyId);
    }

}