package com.example.attendance_management.domain.workTime.repository;

import com.example.attendance_management.domain.member.domain.Member;
import com.example.attendance_management.domain.workTime.domain.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

    Optional<WorkTime> findByMemberAndWorkDate(Member member, LocalDate workDate);

    List<WorkTime> findAllByMemberAndWorkDateAfterAndWorkDateBefore(Member member, LocalDate start, LocalDate end);

}
