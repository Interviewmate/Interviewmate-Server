package org.interviewmate.domain.analysis.api;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.analysis.service.GazeAnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/analyses")
public class AnalysisController {

    private final GazeAnalysisService gazeAnalysisService;

    @GetMapping("/{interviewId}")
    public String createAnalysis(@PathVariable Long interviewId, @RequestParam String objectKey) {

        gazeAnalysisService.createGazeAnalysis(interviewId, objectKey);

        // todo: 행동(자세) 분석

        // todo: 답변 분석


        return "";
    }

}
