package org.interviewmate.domain.interview.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.interviewmate.domain.interview.model.Interview;

@Schema(description = "면접 생성 response dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewCreateResponseDto {
    @Schema(description = "사용자 id")
    @NonNull
    private Long userId;
    @Schema(description = "면접 id")
    @NonNull
    private Long interviewId;
    @Schema(description = "행동 분석 id")
    @NonNull
    private Long behaviorId;

    @Builder
    public InterviewCreateResponseDto(@NonNull Long userId, @NonNull Long interviewId, @NonNull Long behaviorId) {
        this.userId = userId;
        this.interviewId = interviewId;
        this.behaviorId = behaviorId;
    }

    public static InterviewCreateResponseDto of(Interview interview) {
        return InterviewCreateResponseDto.builder()
                .interviewId(interview.getInterId())
                .userId(interview.getUser().getUserId())
                .behaviorId(interview.getBehaviorAnalysis().getBeId())
                .build();
    }
}
