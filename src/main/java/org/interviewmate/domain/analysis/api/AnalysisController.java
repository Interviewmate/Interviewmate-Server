package org.interviewmate.domain.analysis.api;

import static org.interviewmate.global.util.response.ResponseCode.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.analysis.model.dto.response.BehaviorAnalysisFindOutDto;
import org.interviewmate.domain.analysis.service.AnalysisService;
import org.interviewmate.domain.analysis.service.AnswerAnalysisService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "분석 관련 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/analyses")
public class AnalysisController {

    private final AnalysisService analysisService;
    private final AnswerAnalysisService answerAnalysisService;

    @Operation(summary = "분석 요청 API", description = "1개 면접 질문 영상에 대한 시선, 자세, 답변 분석 요청")
    @Parameters({
            @Parameter(name = "interviewId", description = "인터뷰 식별자"),
            @Parameter(name = "objectKey", description = "객체 키"),
            @Parameter(name = "questionId", description = "질문 식별자")
    })
    @PostMapping("/{interviewId}")
    public ResponseEntity<ResponseDto<String>> createAnalysis(@PathVariable Long interviewId, @RequestParam String objectKey,
                                                              @RequestParam("questionId")Long questionId) {

        analysisService.processBehaviorAnalysis(interviewId, objectKey);
        answerAnalysisService.createAnswerAnalysis(interviewId, questionId, objectKey);
        return ResponseUtil.SUCCESS(SUCCESS, "");

    }

    @Operation(summary = "행동 분석 조회 API", description = "선택한 면접에 대한 분석 조회")
    @Parameters({
            @Parameter(name = "interviewId", description = "인터뷰 식별자"),
    })
    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<ResponseDto<BehaviorAnalysisFindOutDto>> findBehaviorAnalysis(@PathVariable Long interviewId) {
        BehaviorAnalysisFindOutDto response = analysisService.findCreateAnalysis(interviewId);
        return ResponseUtil.SUCCESS(SUCCESS, response);
    }

    @Operation(summary = "종합 분석 API", description = "모든 면접 질문 영상에 대한 분석")
    @Parameters({
            @Parameter(name = "userId", description = "인터뷰 식별자"),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<String>> processComprehensiveAnalysis(@PathVariable Long userId) {
        analysisService.processComprehensiveAnalysis(userId);
        return ResponseUtil.SUCCESS(SUCCESS, "");
    }

}
