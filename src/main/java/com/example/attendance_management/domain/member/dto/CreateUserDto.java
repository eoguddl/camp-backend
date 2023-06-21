package com.example.attendance_management.domain.member.dto;

import lombok.Data;

@Data
public class CreateUserDto {

    private String name;

    private String department;

    private String position;

    private String username;

    private String password;

}
