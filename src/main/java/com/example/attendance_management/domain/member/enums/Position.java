package com.example.attendance_management.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {

    EMPLOYEE("직원"),

    INTERN("인턴"),

    STAFF("사원"),

    ASSISTANT_MANAGER("대리"),

    MANAGER("과장"),

    DEPUTY_GENERAL_MANAGER("차장"),

    GENERAL_MANAGER("부장"),

    EXECUTIVES("임원");

    private final String name;

}
