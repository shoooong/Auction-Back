package com.example.backend.dto.alarm;

import com.example.backend.entity.enumData.AlarmType;
import lombok.*;

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
    private boolean alarmRead;

    public AlarmDto(Long alarmId) {
        this.alarmId = alarmId;
    }
}
