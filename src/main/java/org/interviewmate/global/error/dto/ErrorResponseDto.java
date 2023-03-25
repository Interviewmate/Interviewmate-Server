package org.interviewmate.global.error.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {

    private String timestamp;
    private String trackingId;
    private int code;
    private String messgae;
    private String detailMessage;


    public ErrorResponseDto(CustomException e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.code = e.getErrorCode().getStatus();
        this.messgae = e.getErrorCode().getMessage();
        this.detailMessage = e.getMessage();
    }

    public ErrorResponseDto(ErrorCode e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.code = e.getStatus();
        this.messgae = e.getMessage();
        this.detailMessage = e.getMessage();
    }


    public static ErrorResponseDto of(ErrorCode code) {
        return new ErrorResponseDto(code);
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
