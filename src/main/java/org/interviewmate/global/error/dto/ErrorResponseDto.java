package org.interviewmate.global.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ErrorResponseDto {

    private String timestamp;

    private String trackingId;

    @JsonInclude(Include.NON_NULL)
    private String message;

    @JsonInclude(Include.NON_NULL)
    private Map<String, String> descriptions;

    @Builder
    public static ErrorResponseDto of(Map<String, String> descriptions) {
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .trackingId(UUID.randomUUID().toString())
                .descriptions(descriptions)
                .build();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

