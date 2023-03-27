package org.interviewmate.global.error.dto;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ErrorResponseDto {
    private String timestamp;
    private String trackingId;

    public ErrorResponseDto() {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
    }


    public static ErrorResponseDto of() {
        return new ErrorResponseDto();
    }
}
