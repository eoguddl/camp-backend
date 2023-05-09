package com.example.attendance_management.domain.remoteWork.repository;

import com.example.attendance_management.domain.member.domain.Member;
import com.example.attendance_management.domain.remoteWork.domain.RemoteWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemoteWorkRepository extends JpaRepository<RemoteWork, Long> {

    List<RemoteWork> findAllByMember(Member member);

}
