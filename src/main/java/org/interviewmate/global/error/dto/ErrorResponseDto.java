package org.interviewmate.global.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ErrorResponseDto {
    private String timestamp;
    private String trackingId;
    @JsonInclude(Include.NON_NULL)
    private String description;

    public ErrorResponseDto() {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
    }

    public ErrorResponseDto(String description) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.description = description;
    }

    public static ErrorResponseDto of() {
        return new ErrorResponseDto();
    }

    public static ErrorResponseDto of(String description) {
        return new ErrorResponseDto(description);
    }

}
