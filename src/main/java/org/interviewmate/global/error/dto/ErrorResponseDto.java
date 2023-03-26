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


    public ErrorResponseDto(CustomException e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
    }

    public ErrorResponseDto(ErrorCode e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
    }


    public static ErrorResponseDto of(ErrorCode code) {
        return new ErrorResponseDto(code);
    }
}
