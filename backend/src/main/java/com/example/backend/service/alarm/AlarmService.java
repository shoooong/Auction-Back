package com.example.backend.service.alarm;
import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.backend.entity.QAlarm.alarm;

@Log4j2
@RequiredArgsConstructor
@Service
public class AlarmService {
    private AlarmRepository alarmRepository;

    private final static Long DEFAULT_TIMEOUT = 3600000L;
    private final ConcurrentHashMap<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<Alarm>> userNoti =new ConcurrentHashMap<>();


    public SseEmitter subscribe(Long userId){
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        userEmitters.put(userId, emitter);

        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError(e -> userEmitters.remove(userId));

        return emitter;

    }

    // 알람 sseEmitter 저장
    @Transactional
    public void saveAlarm(Long userId, AlarmType alarmType) {

//        CopyOnWriteArrayList<Alarm> notifications = userNoti.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>());
        Alarm alarm = RequestAlarmDto.toEntity(userId, alarmType);
        sendNotification(userId, alarm);

    }

    // 알림들 가져오기
    @Async
    public void sendAlarmNotification(Long userId) {

        SseEmitter emitter = userEmitters.get(userId);

        try {
            if (emitter != null) {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(userId))
                        .name("alarm")
                        .data(emitter));
//                emitter.send(SseEmitter.event().name("alarm").data(notifications));
            }
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    // 알림 보내기
    @Async
    public void sendNotification(Long userId, Alarm alarm){
        SseEmitter emitter = userEmitters.get(userId);

        try {
            if (emitter != null) {
                emitter.send(SseEmitter.event().name("alarm").data(alarm).id(String.valueOf(alarm.getAlarmId())));
            }
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
