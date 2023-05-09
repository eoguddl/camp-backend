package com.example.attendance_management.domain.remoteWork.dto;

import com.example.attendance_management.domain.remoteWork.domain.RemoteWork;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RemoteWorkRequest {

    @NotBlank(message = "신청사유를 확인해주세요.")
    private String content;

    @Past(message = "근무기간을 확인해주세요.")
    private LocalDate start;

    @Past(message = "근무기간을 확인해주세요.")
    private LocalDate end;

    public RemoteWork toEntity() {
        return RemoteWork.builder()
                .content(this.content)
                .start(this.start)
                .end(this.end)
                .approve(1)
                .build();
    }

}
