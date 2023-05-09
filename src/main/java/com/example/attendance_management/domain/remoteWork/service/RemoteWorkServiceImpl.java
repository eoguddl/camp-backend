package com.example.attendance_management.domain.remoteWork.service;

import com.example.attendance_management.domain.member.domain.Member;
import com.example.attendance_management.domain.member.repository.MemberRepository;
import com.example.attendance_management.domain.remoteWork.domain.RemoteWork;
import com.example.attendance_management.domain.remoteWork.repository.RemoteWorkRepository;
import com.example.attendance_management.domain.remoteWork.dto.RemoteWorkRequest;
import com.example.attendance_management.global.response.BasicResponse;
import com.example.attendance_management.global.security.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteWorkServiceImpl implements RemoteWorkService {

    private final MemberRepository memberRepository;
    
    private final RemoteWorkRepository remoteWorkRepository;

    private final JWTProvider jwtProvider;

    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<BasicResponse> getRemoteWork() {
        Optional<Member> memberOpt = jwtProvider.findMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        List<RemoteWork> remoteWorks = remoteWorkRepository.findAllByMember(member);

        if (remoteWorks.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("재택근무 신청내역이 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("재택근무 신청내역을 정상적으로 찾았습니다.")
                .count(remoteWorks.size())
                .result(remoteWorks)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> applicationRemoteWork(RemoteWorkRequest request) {
        Optional<Member> memberOpt = jwtProvider.findMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        RemoteWork remoteWork = request.toEntity();
        remoteWork.setMember(memberOpt.get());

        remoteWorkRepository.save(remoteWork);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("자택근무 신청이 정상적으로 처리되었습니다.")
                .count(1)
                .result(remoteWork)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> checkRemoteWork(Long id, boolean approve) {
        Optional<RemoteWork> remoteWorkOpt = remoteWorkRepository.findById(id);

        if (remoteWorkOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("재택근무 신청내역이 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        RemoteWork remoteWork = remoteWorkOpt.get();
        remoteWork.setApprove(approve ? 2 : 3);

        remoteWorkRepository.save(remoteWork);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(approve ? "자택근무 신청이 정상적으로 승인되었습니다." : "자택근무 신청이 정상적으로 거절되었습니다.")
                .count(1)
                .result(remoteWork)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

}
