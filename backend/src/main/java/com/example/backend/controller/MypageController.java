package com.example.backend.controller;

import com.example.backend.dto.user.UserModifyDTO;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Log4j2
public class MypageController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 회원 정보 수정
    @PutMapping("/modify")
    public ResponseEntity<?> modifyUser(@RequestBody UserModifyDTO userModifyDTO){
        log.info("UserModifyDTO: {}", userModifyDTO);

        userService.modifyUser(userModifyDTO);

        return ResponseEntity.ok().build();
    }

}
