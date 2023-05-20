package org.interviewmate.domain.interview.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;

@Schema(description = "월별 면접 조회 request dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFindMonthlyRequestDto {
    @Schema(description = "사용자 id", example = "1")
    @NonNull
    private Long userId;

    @Schema(description = "조회할 달", example = "2023-03")
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth yearMonth;
}
