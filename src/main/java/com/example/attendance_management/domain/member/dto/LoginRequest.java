package com.example.attendance_management.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "아이디를 확인해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 확인해주세요.")
    private String password;

}
