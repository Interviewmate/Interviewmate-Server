package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class  AiServerBehaviorAnalysisVO {

    @JsonProperty("video_duration")
    private Double videoDuration;

    @JsonProperty("gazeInfo")
    private AiServerGazeAnalysisResultVO gazeAnalysisResults;

    @JsonProperty("poseInfo")
    private AiServerPoseAnalysisResultVO poseAnalysisResults;

}
