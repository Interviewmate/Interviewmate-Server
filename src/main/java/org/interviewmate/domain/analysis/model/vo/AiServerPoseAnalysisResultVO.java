package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AiServerPoseAnalysisResultVO {

    private Double totalTime;

    @JsonProperty("unstable_list")
    private List<AnalysisDataVO> analysisData = new ArrayList<>();

}
