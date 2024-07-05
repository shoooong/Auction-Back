package com.example.backend.service.alarm;
import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.AlarmType;
import com.example.backend.repository.User.UserRepository;
import com.example.backend.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>() {};
    private final static Long DEFAULT_TIMEOUT = 3600000L;

    public SseEmitter subscribe(Long userId){
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        userEmitters.put(userId, sseEmitter);
//        userEmitters.put(userDTO.getUserId(), sseEmitter);

        sseEmitter.onCompletion(()->userEmitters.remove(userId));
        sseEmitter.onTimeout(()->userEmitters.remove(userId));
        sseEmitter.onError((e)-> userEmitters.remove(userId));

        return sseEmitter;
    }

    // 알람저장
//    @Transactional
    public void saveAlarm(Long userId, AlarmType alarmType) throws IOException {

        Alarm newAlarm = RequestAlarmDto.toEntity(userId, alarmType);

        Alarm savedAlarm = alarmRepository.save(newAlarm);
        getAllAlarmList(savedAlarm);
    }

    // 알림불러오기
//    @Transactional
//    @Async
    public void getAllAlarmList(Alarm alarm) throws IOException {

        List<ResponseAlarmDto> list = alarmRepository.findByUsersUserId(alarm.getUsers().getUserId());
        SseEmitter sseEmitter = userEmitters.get(alarm.getUsers().getUserId());

        sseEmitter.send(SseEmitter.event().name("alarm").data(list));
//        for (SseEmitter emitter : sseEmitter) {
//            try {
//                // 알림 보내기
//                emitter.send(SseEmitter.event().name("alarm").data(list));
//
//            } catch (IOException e) {
//                emitters.remove(emitter);
//            }
//        }

    };

}
