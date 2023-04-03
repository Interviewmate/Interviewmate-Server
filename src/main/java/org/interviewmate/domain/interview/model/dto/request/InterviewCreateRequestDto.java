package org.interviewmate.domain.interview.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "면접 생성 request dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewCreateRequestDto {
    @Schema(description = "사용자 id")
    @NonNull
    private Long userId;
}
