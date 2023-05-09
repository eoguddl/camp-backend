package com.example.attendance_management.domain.workTime.service;

import com.example.attendance_management.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface WorkService {

    ResponseEntity<BasicResponse> getWorkHistory(LocalDate startDate, LocalDate endDate);

    ResponseEntity<BasicResponse> goToWork(String username);

    ResponseEntity<BasicResponse> leaveWork(String username);

}
