package com.example.attendance_management.domain.member.dto;

import com.example.attendance_management.domain.workTime.domain.WorkTime;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyInfoResponse {

    private String name;

    private String department;

    private String position;

    private WorkTime workTime;

    private List<WorkTime> workTimes;

    private String myWorkTime;

    private String myLeftTime;

    private int workRatio;

    private int leftRatio;

}
