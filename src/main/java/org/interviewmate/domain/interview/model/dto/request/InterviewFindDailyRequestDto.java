package org.interviewmate.domain.interview.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "일별 면접 조회 request dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFindDailyRequestDto {
    @Schema(description = "사용자 id", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "조회할 날짜", example = "2023-03-30")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
