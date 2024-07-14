package com.example.backend.dto.alarm;

import com.example.backend.entity.Alarm;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.AlarmType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
public class RequestAlarmDto {

    private Long userId;
    private AlarmType alarmType;
    private LocalDate alarmDate;
    private boolean alarmRead;


    public static Alarm toEntity(Long userId, AlarmType alarmType){
        return Alarm.builder()
                .users(Users.builder().userId(userId).build())
                .alarmType(alarmType)
                .alarmDate(LocalDateTime.now())
                .alarmRead(false)
                .build();
    }


}
