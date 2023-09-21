package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AiServerGazeAnalysisResultVO {

    private Double totalTime;

    @JsonProperty("in_out_tuple_list")
    private List<DataVO> analysisData = new ArrayList<>();

}
