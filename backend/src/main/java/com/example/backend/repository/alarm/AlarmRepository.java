package com.example.backend.repository.alarm;

import com.example.backend.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUsersUserId(Long userId);

    void save(Long userId);
}
