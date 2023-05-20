package org.interviewmate.domain.interview.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Schema(description = "면접 생성 response dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewCreateResponseDto {
    @Schema(description = "면접 id")
    @NotNull
    private Long interviewId;

}
