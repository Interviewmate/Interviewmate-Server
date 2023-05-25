package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AnalysisDataVO {

    //private Long outInId;

    private Double startSec;

    //private Long start_Frame;

    private Double endSec;

    //private Long endFrame;

    private Double duringSec;

    //private Long duringFrame;

}
