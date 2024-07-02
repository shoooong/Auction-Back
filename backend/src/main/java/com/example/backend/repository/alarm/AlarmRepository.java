package com.example.backend.repository.alarm;

import com.example.backend.dto.alarm.AlarmDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUsersUserId(Long userId);
}
