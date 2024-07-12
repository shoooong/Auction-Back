package com.example.backend.repository.alarm;

import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.enumData.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long>  {

    List<Alarm> findByUsersUserId(Long userId);

}
