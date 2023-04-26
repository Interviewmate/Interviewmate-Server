package org.interviewmate.domain.interview.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Schema(description = "면접 질문 요청 request dto")
@Getter
@NoArgsConstructor
public class InterviewGetQuestionRequest {
    @Schema(description = "사용자 id")
    @NotNull
    private Long userId;

    @Schema(description = "면접 id")
    @NotNull
    private Long interviewId;

    @Schema(description = "질문 개수", example = "10")
    @NotNull
    private Integer number;

}
