package com.example.attendance_management.domain.workTime.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkTimeRequest {

    private LocalDate start;

    private LocalDate end;

}
