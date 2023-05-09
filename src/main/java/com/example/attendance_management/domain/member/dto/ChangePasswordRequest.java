package com.example.attendance_management.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    @NotBlank(message = "이전 비밀번호를 확인해주세요.")
    private String prePassword;

    @NotBlank(message = "새로운 비밀번호를 확인해주세요.")
    private String newPassword;

    @NotBlank(message = "비밀번호가 맞는 지 확인해주세요.")
    private String checkPassword;

}
