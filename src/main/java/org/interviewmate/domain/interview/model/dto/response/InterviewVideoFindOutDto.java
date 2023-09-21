package org.interviewmate.domain.interview.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "면접 영상 조회 response")
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class InterviewVideoFindOutDto {

    @Schema(description = "면접 영상 조회 URL")
    private List<String> urls;

    public static InterviewVideoFindOutDto of(List<String> urls) {
        return InterviewVideoFindOutDto.builder()
                .urls(urls)
                .build();
    }


}
