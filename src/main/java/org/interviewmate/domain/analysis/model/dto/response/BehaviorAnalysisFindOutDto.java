package org.interviewmate.domain.analysis.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.analysis.model.vo.BehaviorAnalysisVO;

@Schema(name = "행동 분석 Response", description = "면접에 대한 행동 분석 결과")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BehaviorAnalysisFindOutDto {

    private Long score;
    private List<BehaviorAnalysisVO> behaviorAnalyses;

    public static BehaviorAnalysisFindOutDto of(Long score, List<BehaviorAnalysisVO> behaviorAnalyses) {
       return BehaviorAnalysisFindOutDto.builder()
               .score(score)
               .behaviorAnalyses(behaviorAnalyses)
               .build();
    }
}
