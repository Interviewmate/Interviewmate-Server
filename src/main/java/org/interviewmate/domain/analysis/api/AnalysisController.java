package org.interviewmate.domain.analysis.api;

import static org.interviewmate.global.util.response.ResponseCode.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.analysis.service.AnswerAnalysisService;
import org.interviewmate.domain.analysis.service.BehaviorAnalysisService;
import org.interviewmate.domain.analysis.service.GazeAnalysisService;
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

    private final GazeAnalysisService gazeAnalysisService;
    private final BehaviorAnalysisService behaviorAnalysisService;
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

//        gazeAnalysisService.createGazeAnalysis(interviewId, objectKey);
//        behaviorAnalysisService.createBehaviorAnalysis(interviewId, objectKey);
        // todo: 답변 분석
        answerAnalysisService.createAnswerAnalysis(interviewId, questionId, objectKey);

        return ResponseUtil.SUCCESS(SUCCESS, "");
    }

}
