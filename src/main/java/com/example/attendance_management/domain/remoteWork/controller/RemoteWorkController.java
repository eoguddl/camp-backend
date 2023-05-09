package com.example.attendance_management.domain.remoteWork.controller;

import com.example.attendance_management.domain.remoteWork.dto.RemoteWorkRequest;
import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.domain.remoteWork.service.RemoteWorkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/remotework")
@Tag(name = "RemoteWork", description = "remoteWork rest api")
public class RemoteWorkController {

    private final RemoteWorkService remoteWorkService;

    @GetMapping
    public ResponseEntity<BasicResponse> getRemoteWork() {
        return remoteWorkService.getRemoteWork();
    }

    @PostMapping
    public ResponseEntity<BasicResponse> applicationRemoteWork(@RequestBody @Valid RemoteWorkRequest request) {
        return remoteWorkService.applicationRemoteWork(request);
    }

}
