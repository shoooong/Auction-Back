package com.example.backend.repository;

import com.example.backend.entity.Alarm;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.alarm.AlarmRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class AlarmRepositoryTests {

    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void insert(){
        Users users = userRepository.findById(1L).orElseThrow();

        Alarm alarm = Alarm.builder()
                .alarmRead(false)
                .alarmType(AlarmType.STYLE)
                .alarmDate(LocalDate.now())
                .users(users)
                .build();

        alarmRepository.save(alarm);
    }
}
