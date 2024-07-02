package com.example.backend.service;

import com.example.backend.dto.alarm.AlarmDto;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public List<AlarmDto> getAllAlarmList(Long userId){

        return alarmRepository.findByUsersUserId(userId).stream().map(AlarmDto::fromEntity).collect(toList());

    }
}
