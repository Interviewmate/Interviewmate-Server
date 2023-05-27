package org.interviewmate.domain.analysis.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.analysis.model.vo.AnalysisScoreVO;

@Schema(name = "종합 분석 Response", description = "모든 면접에 대한 분석 결과 제공")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ComprehensiveAnalysisProcessOutDto {

    private Long averageInterviewScore;
    private List<AnalysisScoreVO> gazeScore;
    private List<AnalysisScoreVO> poseScore;

    public static ComprehensiveAnalysisProcessOutDto of(
            Long totalScore, List<AnalysisScoreVO> gazeScore, List<AnalysisScoreVO> poseScore) {
        return ComprehensiveAnalysisProcessOutDto.builder()
                .averageInterviewScore(totalScore)
                .gazeScore(gazeScore)
                .poseScore(poseScore)
                .build();
    }



}
