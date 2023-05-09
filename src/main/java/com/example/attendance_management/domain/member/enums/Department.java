package com.example.attendance_management.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Department {

    PLANNING_DEPT("기획부"),

    TECHNICAL_RESEARCH_CENTER("기술연구소"),

    NETWORK_BUSINESS_DEVISION("네트워크사업부"),

    GENERAL_AFFAIRS_DEPT("총무부"),

    R_A_D_TEAM("개발팀"),

    MARKETING_TEAM("마케팅팀"),

    S_A_M_TEAM("영업팀");

    private final String name;

}
