package org.interviewmate.domain.analysis.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.analysis.model.AnalysisData;
import org.interviewmate.domain.analysis.model.vo.AnalysisDataVO;

@Schema(name = "행동 분석 Response", description = "면접에 대한 행동 분석 결과")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BehaviorAnalysisFindOutDto {

    private String url;
    private String question;
    private Long score;
    private List<AnalysisData> gazeAnalysis;
    private List<AnalysisData> poseAnalysis;

    public static BehaviorAnalysisFindOutDto of(
            String url, String question, Long score, List<AnalysisData> gazeAnalysis, List<AnalysisData> poseAnalysis
    ) {
       return BehaviorAnalysisFindOutDto.builder()
               .url(url)
               .question(question)
               .score(score)
               .gazeAnalysis(gazeAnalysis)
               .poseAnalysis(poseAnalysis)
               .build();
    }
}
