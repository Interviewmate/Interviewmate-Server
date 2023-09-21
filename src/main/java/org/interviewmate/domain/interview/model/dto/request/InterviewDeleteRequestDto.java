package org.interviewmate.domain.interview.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "면접 삭제 request dto")
@Getter
@NoArgsConstructor
public class InterviewDeleteRequestDto {
    @Schema(description = "interview id")
    @NotNull
    private Long interId;
}
