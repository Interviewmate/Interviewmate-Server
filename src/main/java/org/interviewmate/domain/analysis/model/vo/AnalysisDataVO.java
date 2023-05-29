package org.interviewmate.domain.analysis.model.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisDataVO {

    private Double startSec;
    private Double endSec;
    private Double duringSec;

    @Builder
    public AnalysisDataVO(Double startSec, Double endSec, Double duringSec) {
        this.startSec = startSec;
        this.endSec = endSec;
        this.duringSec = duringSec;
    }

}
