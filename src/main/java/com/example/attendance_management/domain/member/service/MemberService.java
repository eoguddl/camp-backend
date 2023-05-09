package com.example.attendance_management.domain.member.service;

import com.example.attendance_management.domain.member.dto.ChangePasswordRequest;
import com.example.attendance_management.domain.member.dto.LoginRequest;
import com.example.attendance_management.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    ResponseEntity<BasicResponse> myInfo();

    ResponseEntity<BasicResponse> login(LoginRequest loginRequest);

    ResponseEntity<BasicResponse> changePassword(ChangePasswordRequest changePasswordRequest);

}
