package com.example.attendance_management.domain.member.service;

import com.example.attendance_management.domain.member.domain.Member;
import com.example.attendance_management.domain.member.dto.*;
import com.example.attendance_management.domain.member.enums.Role;
import com.example.attendance_management.domain.member.repository.MemberRepository;
import com.example.attendance_management.domain.workTime.domain.WorkTime;
import com.example.attendance_management.domain.workTime.repository.WorkTimeRepository;
import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.global.security.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final WorkTimeRepository workTimeRepository;

    private final JWTProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<BasicResponse> createUser(CreateUserDto dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .role(Role.USER.getKey())
                .build();

        memberRepository.save(member);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("사용자를 정상적으로 찾았습니다.")
                .count(1)
                .result(member)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> myInfo() {
        Optional<Member> memberOpt = jwtProvider.findMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        WorkTime workTime = workTimeRepository.findByMemberAndWorkDate(member, LocalDate.now()).orElse(null);

        YearMonth today = YearMonth.now();
        LocalDate startDate = today.atDay(1);
        LocalDate endDate = LocalDate.now();

        List<WorkTime> workTimes = workTimeRepository.findAllByMemberAndWorkDateAfterAndWorkDateBefore(member, startDate, endDate);

        MyInfoResponse response = MyInfoResponse.builder()
                .name(member.getName())
                .department(member.getDepartment())
                .position(member.getPosition())
                .workTime(workTime)
                .workTimes(workTimes)
                .build();

        if (workTime != null && workTime.getStart() != null) {
            LocalDateTime start = workTime.getStart();
            LocalDateTime nowTime = LocalDateTime.now();
            Duration diff = Duration.between(start.toLocalTime(), nowTime.toLocalTime());
            long s = diff.getSeconds();
            long leftTime = 28800 - s;
            response.setMyWorkTime(String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));
            response.setMyLeftTime(String.format("%d:%02d:%02d", leftTime / 3600, (leftTime % 3600) / 60, (leftTime % 60)));
            response.setWorkRatio((int) ((double) s / (double) 28800 * 100.0));
            response.setLeftRatio((int) ((double) leftTime / (double) 28800 * 100.0));
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("사용자를 정상적으로 찾았습니다.")
                .count(1)
                .result(response)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> login(LoginRequest request) {
        Optional<Member> memberOpt = memberRepository.findByUsername(request.getUsername());

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("비밀번호가 일치하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        String accessToken = jwtProvider.createToken(member.getUsername(), member.getRole());
        LoginResponse loginResponse = new LoginResponse(member);
        loginResponse.setToken(accessToken);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("로그인 성공")
                .count(1)
                .result(loginResponse)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> changePassword(ChangePasswordRequest request) {
        Optional<Member> memberOpt = jwtProvider.findMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        if (!passwordEncoder.matches(request.getPrePassword(), member.getPassword())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("비밀번호가 일치하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (!request.getNewPassword().equals(request.getCheckPassword())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("비밀번호와 비밀번호 확인이 일치하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        member.setPassword(passwordEncoder.encode(request.getNewPassword()));
        memberRepository.save(member);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("비밀번호가 정상적으로 변경되었습니다.")
                .count(1)
                .result(member)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

}
