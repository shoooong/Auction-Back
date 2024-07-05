package com.example.backend.repository.alarm;

import com.example.backend.dto.alarm.RequestAlarmDto;
import com.example.backend.dto.alarm.ResponseAlarmDto;
import com.example.backend.entity.Alarm;
import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
//    List<Alarm> findByUsers(Users user);
    List<ResponseAlarmDto> findByUsersUserId(Long userId);

//    void deleteByUserId(Long userId);
}
