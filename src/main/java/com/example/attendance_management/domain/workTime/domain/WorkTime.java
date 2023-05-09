package com.example.attendance_management.domain.workTime.domain;

import com.example.attendance_management.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private Long workTime;

    @Column(nullable = false)
    private LocalDate workDate;

    @ManyToOne
    private Member member;

    // 1: 미출근 // 2: 출근 // 3: 퇴근
    @Column(nullable = false)
    private Integer status;

    public WorkTime(Member member) {
        this.workDate = LocalDate.now();
        this.member = member;
        this.status = 1;
    }
}
