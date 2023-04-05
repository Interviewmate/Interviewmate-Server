package org.interviewmate.domain.interview.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Schema(description = "면접 생성 request dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewCreateRequestDto {
    @Schema(description = "사용자 id")
    @NotNull
    private Long userId;
}
