package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.analysis.model.AiServerBehaviorAnalysisResult;
import org.interviewmate.domain.analysis.model.AiServerGazeAnalysisResult;

@Getter
@NoArgsConstructor
public class AiServerBehaviorAnalysisVO {

    @JsonProperty("unstable_list")
    private List<AiServerBehaviorAnalysisResult> aiServerBehaviorAnalysisResults = new ArrayList<>();

}
