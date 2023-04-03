package org.interviewmate.domain.interview.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.interviewmate.domain.interview.model.Interview;
import java.time.LocalTime;

@Schema(description = "월별 면접 조회 response dto")
@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
public class InterviewFindDailyResponseDto {
    @Schema(description = "interview id",example = "3")
    @NonNull
    private Long interviewId;

    @Schema(description = "시간", example = "20:00:00")
    @NonNull
    private LocalTime time;

    public static InterviewFindDailyResponseDto of(Interview interview) {
        return InterviewFindDailyResponseDto.builder()
                .interviewId(interview.getInterId())
                .time(interview.getCreatedAt().toLocalTime())
                .build();
    }
}
