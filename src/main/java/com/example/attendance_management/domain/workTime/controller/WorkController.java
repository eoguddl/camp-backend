package com.example.attendance_management.domain.workTime.controller;

import com.example.attendance_management.domain.workTime.dto.WorkTimeRequest;
import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.domain.workTime.service.WorkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work")
@Tag(name = "Work", description = "work rest api")
public class WorkController {

    private final WorkService workService;

    @GetMapping
    public ResponseEntity<BasicResponse> getWorkHistory(@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
        return workService.getWorkHistory(startDate, endDate);
    }

    @PutMapping("/go")
    public ResponseEntity<BasicResponse> goToWork(@RequestParam String username) {
        return workService.goToWork(username);
    }

    @PutMapping("/leave")
    public ResponseEntity<BasicResponse> leaveWork(@RequestParam String username) {
        return workService.leaveWork(username);
    }

}
