package com.example.backend.dto.alarm;

import com.example.backend.entity.Alarm;
import com.example.backend.entity.enumData.AlarmType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
public class AlarmDto {

    private Long alarmId;
    private String alarmTitle;
    private String alarmContent;
    private AlarmType alarmType;
    private LocalDate alarmDate;
    private boolean alarmRead;

    public static AlarmDto fromEntity(Alarm alarm){
        return AlarmDto.builder()
                .alarmId(alarm.getAlarmId())
                .alarmTitle(alarm.getAlarmTitle())
                .alarmContent(alarm.getAlarmContent())
                .alarmType(alarm.getAlarmType())
                .alarmRead(alarm.getAlarmRead())
                .alarmDate(alarm.getAlarmDate())
                .build();
    }

}
