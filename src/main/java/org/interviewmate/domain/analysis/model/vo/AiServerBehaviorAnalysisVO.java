package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class  AiServerBehaviorAnalysisVO {

    @JsonProperty("gazeInfo")
    private AiServerGazeAnalysisResultVO gazeAnalysisResults;

    @JsonProperty("poseInfo")
    private AiServerPoseAnalysisResultVO poseAnalysisResults;

    @JsonProperty("video_duration")
    private Double videoDuration;

}
