package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Log4j2
@Controller
public class OrderController {
    private final UserRepository userRepository;

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
