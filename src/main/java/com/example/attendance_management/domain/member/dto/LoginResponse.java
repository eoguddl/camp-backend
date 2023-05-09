package com.example.attendance_management.domain.member.dto;

import com.example.attendance_management.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String name;

    private String username;

    private List<String> roles;

    private String token; 

    public LoginResponse(Member member) {
        this.name = member.getName();
        this.username = member.getUsername();
        this.roles = List.of(member.getRole());
    }

}
