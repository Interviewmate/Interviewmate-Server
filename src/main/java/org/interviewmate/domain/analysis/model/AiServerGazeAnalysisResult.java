package org.interviewmate.domain.analysis.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AiServerGazeAnalysisResult {

    //private Long outInId;

    private Long outSec;

    //private Long outFrame;

    private Long inSec;

    //private Long in_Frame;

    private Long duringSec;

    //private Long duringFrame;

}
