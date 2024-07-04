package com.example.backend.service.alarm;

import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.management.Notification;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    // 받은 알림들 리스트화
    private final ArrayList<SseEmitter> emitters = new ArrayList<>();
    private final static Long DEFAULT_TIMEOUT = 3600000L;

    public SseEmitter subscribe(){
        // sse 생성
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitters.add(sseEmitter);

        sseEmitter.onCompletion(()->emitters.remove(sseEmitter));
        sseEmitter.onTimeout(emitters::clear);
        sseEmitter.onError((e)-> emitters.remove(sseEmitter));

        return sseEmitter;
    }

    // 모든 알람 리스트 가져오기
    public List<ResponseAlarmDto> getAllAlarmList(Long userId){
        List<ResponseAlarmDto> list = alarmRepository.findByUsersUserId(userId).stream().map(ResponseAlarmDto::fromEntity).collect(toList());

        for (SseEmitter emitter : emitters) {
            try {
                // 알림 보내기
                emitter.send(SseEmitter.event().name("alarm").data(list));

            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }

        return list;
    }

}
