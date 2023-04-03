package org.interviewmate.domain.interview.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "월별 면접 조회 response dto")
@Getter
@AllArgsConstructor
public class InterviewFindMonthlyResponseDto {
    @Schema(description = "list size (date 개수)",example = "3")
    @NotNull
    private Long count;

    @Schema(description = "면접 기록이 있는 날짜 list", example = "[3,4,10]")
    @NotNull
    private List<Integer> dateList;

    public InterviewFindMonthlyResponseDto() {
        this.count = 0L;
        this.dateList = new ArrayList<>();
    }
    public void setCount(long count) {
        this.count = count;
    }

    public void addDateList(int date) {
        this.dateList.add(date);
    }
}
