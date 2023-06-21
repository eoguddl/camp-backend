package com.example.attendance_management.domain.member.domain;

import com.example.attendance_management.domain.member.dto.MemberResponse;
import com.example.attendance_management.domain.remoteWork.domain.RemoteWork;
import com.example.attendance_management.domain.workTime.domain.WorkTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // 근무지
    private String workPlace;

    // 부서
    @Column(nullable = false)
    private String department;

    // 직급
    @Column(nullable = false)
    private String position;

    @OneToMany
    private List<WorkTime> workTimes;

    @OneToMany
    private List<RemoteWork> remoteWorks;

    @Column(nullable = false)
    private String role;

}
