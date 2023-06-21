package com.example.attendance_management.domain.member.dto;

import com.example.attendance_management.domain.workTime.domain.WorkTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberResponse {

    private Long id;

    private String name;

    private String username;

    private String department;

    private String position;

    private WorkTime todayWorkTime;

    private List<WorkTime> workTimes;

}
