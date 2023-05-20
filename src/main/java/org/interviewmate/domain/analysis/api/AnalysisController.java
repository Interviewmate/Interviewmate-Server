package org.interviewmate.domain.analysis.api;

import static org.interviewmate.global.util.response.ResponseCode.*;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.analysis.service.GazeAnalysisService;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/analyses")
public class AnalysisController {

    private final GazeAnalysisService gazeAnalysisService;

    @PostMapping("/{interviewId}")
    public ResponseEntity<ResponseDto<String>> createAnalysis(@PathVariable Long interviewId, @RequestParam String objectKey) {

        gazeAnalysisService.createGazeAnalysis(interviewId, objectKey);

        // todo: 행동(자세) 분석

        // todo: 답변 분석


        return ResponseUtil.SUCCESS(SUCCESS, "");
    }

}
