package com.example.attendance_management.domain.remoteWork.service;

import com.example.attendance_management.domain.remoteWork.dto.RemoteWorkRequest;
import com.example.attendance_management.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RemoteWorkService {

    ResponseEntity<BasicResponse> getRemoteWork();

    ResponseEntity<BasicResponse> applicationRemoteWork(RemoteWorkRequest request);

    ResponseEntity<BasicResponse> checkRemoteWork(Long id, boolean approve);

}
