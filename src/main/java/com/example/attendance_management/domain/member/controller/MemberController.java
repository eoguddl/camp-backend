package com.example.attendance_management.domain.member.controller;

import com.example.attendance_management.domain.member.dto.ChangePasswordRequest;
import com.example.attendance_management.domain.member.dto.LoginRequest;
import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Member", description = "member rest api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my-info")
    public ResponseEntity<BasicResponse> myInfo() {
        return memberService.myInfo();
    }

    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody @Valid LoginRequest request) {
        return memberService.login(request);
    }

    @PutMapping("/change/password")
    public ResponseEntity<BasicResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return memberService.changePassword(request);
    }

}
