package com.example.attendance_management.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    USER("ROLE_USER", "사용자"),

    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;

    private final String value;

}
