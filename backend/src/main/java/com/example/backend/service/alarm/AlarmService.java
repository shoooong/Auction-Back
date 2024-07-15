package com.example.backend.service.alarm;
import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.dto.luckyDraw.DrawDto;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

@Log4j2
@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    private final static Long DEFAULT_TIMEOUT = 3600000L;
    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    private final SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

    public SseEmitter subscribe(Long userId){
        userEmitters.put(userId, emitter);

        if (userEmitters.containsKey(userId)){
            SseEmitter existingEmitter = userEmitters.get(userId);
            if (existingEmitter != null) {
                existingEmitter.complete();
                userEmitters.remove(userId);
            }
        }


        // 상황별 emitter 삭제 처리
        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError(e -> userEmitters.remove(userId));

        // 기존 알람 데이터 전송
        sendAlarmNotification(userId);
        return emitter;
    }

    @Scheduled(fixedRate = 45 * 1000)
    public void sendHeartBeat(){
        try {
            emitter.send(SseEmitter.event()
                    .comment("alarm beat"));
            log.info("슬기 alarm beat");
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    // 알림들 가져오기
    @Async
    public void sendAlarmNotification(Long userId) {
        List<ResponseAlarmDto> list = alarmRepository.findByUsersUserId(userId).stream().map(ResponseAlarmDto::fromEntity).collect(toList());
        SseEmitter emitter = userEmitters.get(userId);

        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(userId))
                    .name("alarm-list")
                    .data(list));
        } catch (IOException e) {
            userEmitters.remove(userId,emitter);
        }


    }

    // 알람 저장하고 보내기
    @Transactional
    public void sendNotification(Long userId, AlarmType alarmType){

        Alarm newAlarm = alarmRepository.save(RequestAlarmDto.toEntity(userId, alarmType));
        ResponseAlarmDto responseAlarmDto = ResponseAlarmDto.fromEntity(newAlarm);
        SseEmitter emitter = userEmitters.get(userId);
        try {
            if (emitter != null) {
                emitter.send(SseEmitter.event()
                        .name("alarm")
                        .id(String.valueOf(responseAlarmDto.getAlarmId()))
                        .data(responseAlarmDto));
            }
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
