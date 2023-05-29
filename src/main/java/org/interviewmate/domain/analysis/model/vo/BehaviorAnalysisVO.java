package org.interviewmate.domain.analysis.model.vo;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BehaviorAnalysisVO {

    private String url;
    private String question;
    private List<AnalysisDataVO> gazeAnalysis;
    private List<AnalysisDataVO> poseAnalysis;

    @Builder
    public BehaviorAnalysisVO(String url, String question, List<AnalysisDataVO> gazeAnalysis,
                              List<AnalysisDataVO> poseAnalysis) {
        this.url = url;
        this.question = question;
        this.gazeAnalysis = gazeAnalysis;
        this.poseAnalysis = poseAnalysis;
    }
}
