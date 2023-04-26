package org.interviewmate.domain.interview.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Schema(description = "면접 생성 response dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewGetQuestionResponse {
    @Schema(description = "질문 id")
    @NotNull
    private Long questionId;
    @NotNull
    private String content;

}
