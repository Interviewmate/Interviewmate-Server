package org.interviewmate.domain.interview.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Schema(description = "면접 삭제 request dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDeleteRequestDto {
    @Schema(description = "interview id")
    @NonNull
    private Long interId;
}
