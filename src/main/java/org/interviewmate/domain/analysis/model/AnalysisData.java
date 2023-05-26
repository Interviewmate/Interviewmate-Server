package org.interviewmate.domain.analysis.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisData {

    private Double startSec;
    private Double endSec;
    private Double duringSec;

    @Builder
    public AnalysisData(Double startSec, Double endSec, Double duringSec) {
        this.startSec = startSec;
        this.endSec = endSec;
        this.duringSec = duringSec;
    }
}
