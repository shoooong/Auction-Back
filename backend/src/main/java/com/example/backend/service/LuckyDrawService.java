package com.example.backend.service;

import com.example.backend.dto.luckyDraw.LuckyDrawsDTO;
import com.example.backend.repository.LuckyDraw.LuckyDrawRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LuckyDrawService {

    private final LuckyDrawRepository luckyDrawRepository;

    public List<LuckyDrawsDTO> getAllLuckyDraws(){
        return luckyDrawRepository.findAll().stream()
                .map(LuckyDrawsDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public LuckyDrawsDTO getLuckyDrawById(Long luckyId){
        return luckyDrawRepository.findById(luckyId)
                .map(LuckyDrawsDTO::fromEntity)
                .orElseThrow(()-> new RuntimeException("Lucky Draw Not Found: " + luckyId));
    }

}

