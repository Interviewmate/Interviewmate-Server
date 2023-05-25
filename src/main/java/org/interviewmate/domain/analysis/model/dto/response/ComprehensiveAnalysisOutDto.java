package org.interviewmate.domain.analysis.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "종합 분석 Response", description = "모든 면접에 대한 분석 결과 제공")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComprehensiveAnalysisOutDto {

    private Long totalScore;
    private Long behaviorScore;
    private Long gazeScore;

}
