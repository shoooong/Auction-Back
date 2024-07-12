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
import java.util.Map;
import java.util.concurrent.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    private final static Long DEFAULT_TIMEOUT = 3600000L;
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();
    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();


    public SseEmitter subscribe(Long userId, String lastEventId){
        String eventId = userId + "_" + System.currentTimeMillis();

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        eventCache.put(eventId, emitter);
        userEmitters.put(userId, emitter);

        // 상황별 emitter 삭제 처리
        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError(e -> userEmitters.remove(userId));

        // 더미데이터 전송
        sendAlarmNotification(userId, emitter, "event");

//        List<Alarm> list = alarmRepository.findByUsersUserId(userId);
//        for (Alarm alarm : list){
//            sendNotification(alarm);
//        }

        return emitter;

    }

    // 알림들 가져오기
    @Async
    public void sendAlarmNotification(Long userId, SseEmitter emitter, String eventData) {
        try {
            emitter.send(SseEmitter.event()
                    .name("alarm")
                    .id(String.valueOf(userId))
                    .data(eventData));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    // 알람 저장
//    @Transactional
    public void saveAlarm(Long userId, AlarmType alarmType) {
        Alarm newAlarm = alarmRepository.save(RequestAlarmDto.toEntity(userId, alarmType));
        sendNotification(newAlarm);
    }

    // 알림 보내기
    @Async
    public void sendNotification(Alarm alarm){
        SseEmitter emitter = userEmitters.get(alarm.getUsers().getUserId());

        try {
            if (emitter != null) {
                emitter.send(SseEmitter.event()
                        .name("alarm")
                        .id(String.valueOf(alarm.getUsers().getUserId()))
                        .data(alarm));
            }
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

//    public void deleteAlarm(Long userId, Long alarmId){
//        List<ResponseAlarmDto> sseEmitters = alarmRepository.findByUsersUserId(alarm.getUsers().getUserId());
//        for (Alarm sseEmitter : sseEmitters) {
//            try{
//                alarmRepository.save(sseEmitter);
//                sendAlarmNotification(userId, "알림 잘 저장됨");
//            } catch (Exception e) {
//                alarmRepository.delete(sseEmitter);
//            }
//        }
//
//        try {
//
//        } catch (IOException e) {
//            emitter.completeWithError(e);
//        }
//    }
}
