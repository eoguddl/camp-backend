package com.example.attendance_management.domain.member.controller;

import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.domain.remoteWork.service.RemoteWorkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "admin rest api")
public class AdminController {

    private final RemoteWorkService remoteWorkService;

    @PutMapping("/remotework/approve")
    public ResponseEntity<BasicResponse> approveRemoteWork(@RequestParam Long id) {
        return remoteWorkService.checkRemoteWork(id, true);
    }

    @PutMapping("/remotework/deny")
    public ResponseEntity<BasicResponse> denyRemoteWork(@RequestParam Long id) {
        return remoteWorkService.checkRemoteWork(id, false);
    }

}
