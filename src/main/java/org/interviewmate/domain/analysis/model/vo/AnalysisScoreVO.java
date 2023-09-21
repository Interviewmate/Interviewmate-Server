package org.interviewmate.domain.analysis.model.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisScoreVO {

    Long interviewId;
    Long score;

    @Builder
    public AnalysisScoreVO(Long interviewId, Long score) {
        this.interviewId = interviewId;
        this.score = score;
    }

}
