package com.example.attendance_management.domain.workTime.service;

import com.example.attendance_management.domain.member.domain.Member;
import com.example.attendance_management.domain.member.enums.Role;
import com.example.attendance_management.domain.member.repository.MemberRepository;
import com.example.attendance_management.domain.workTime.domain.WorkTime;
import com.example.attendance_management.domain.workTime.repository.WorkTimeRepository;
import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.global.security.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final JWTProvider jwtProvider;

    private final MemberRepository memberRepository;

    private final WorkTimeRepository workTimeRepository;

    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<BasicResponse> getWorkHistory(LocalDate startDate, LocalDate endDate) {
        Optional<Member> memberOpt = jwtProvider.findMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        if (startDate == null || endDate == null) {
            YearMonth today = YearMonth.now();
            startDate = today.atDay(1);
            endDate = today.atEndOfMonth();
        }

        List<WorkTime> workTimes = workTimeRepository.findAllByMemberAndWorkDateAfterAndWorkDateBefore(member, startDate, endDate);
        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("근무내역을 정상적으로 찾았습니다.")
                .count(workTimes.size())
                .result(workTimes)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> goToWork(String username) {
        Optional<Member> memberOpt = memberRepository.findByUsername(username);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }
        
        Member member = memberOpt.get();
        Optional<WorkTime> workTimeOpt = workTimeRepository.findByMemberAndWorkDate(member, LocalDate.now());
        WorkTime workTime;

        if (workTimeOpt.isPresent()) {
            workTime = workTimeOpt.get();
            workTime.setStart(LocalDateTime.now());
            workTime.setStatus(2);
        } else { // 만약에 없을 경우
            workTime = WorkTime.builder()
                    .start(LocalDateTime.now())
                    .workDate(LocalDate.now())
                    .member(member)
                    .status(2)
                    .build();
        }

        workTimeRepository.save(workTime);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("출근이 정상적을 처리되었습니다.")
                .count(1)
                .result(workTime)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> leaveWork(String username) {
        Optional<Member> memberOpt = memberRepository.findByUsername(username);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        Optional<WorkTime> workTimeOpt = workTimeRepository.findByMemberAndWorkDate(member, LocalDate.now());

        if (workTimeOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("출근 기록을 찾지 못하였습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        WorkTime workTime = workTimeOpt.get();
        workTime.setEnd(LocalDateTime.now());
        workTime.setWorkTime(Duration.between(workTime.getStart(), workTime.getEnd()).getSeconds());
        workTime.setStatus(3);

        workTimeRepository.save(workTime);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("퇴근이 정상적으로 처리되었습니다.")
                .count(1)
                .result(workTime)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void workTimeSetting() {
        try {
            List<Member> members = memberRepository.findAllByRole(Role.USER.getKey());

            for (Member member : members) {
                workTimeRepository.save(new WorkTime(member));
            }
        } catch (Exception exception) {
            log.info("error: {}", exception.getMessage());
        }
    }

}
