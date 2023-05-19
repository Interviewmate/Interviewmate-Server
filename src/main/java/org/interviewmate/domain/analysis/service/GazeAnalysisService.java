package org.interviewmate.domain.analysis.service;

import static org.interviewmate.global.error.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.analysis.exception.AnalysisException;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.vo.AiServerGazeAnalysisVO;
import org.interviewmate.domain.analysis.repository.GazeAnalysisRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GazeAnalysisService {

    private final static String BASE_URL = "localhost:5000";
    private final static String GAZE_ANALYSIS_URI = "/gaze_analysis";

    private final InterviewRepository interviewRepository;
    private final GazeAnalysisRepository gazeAnalysisRepository;
    public void createGazeAnalysis(Long interviewId, String objectKey) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        AiServerGazeAnalysisVO response = executeGazeAnalysis(findInterview.getInterId(), objectKey);
        List<GazeAnalysis> gazeAnalyses = response.getAiServerGazeAnalysisResult().stream()
                .map(
                        result -> GazeAnalysis.builder()
                        .interview(findInterview)
                        .start(result.getInSec())
                        .end(result.getOutSec())
                        .duringTIme(result.getDuringSec())
                        .build()).collect(Collectors.toList()
                );

        gazeAnalysisRepository.saveAll(gazeAnalyses);

    }

    private static AiServerGazeAnalysisVO executeGazeAnalysis(Long interviewId, String objectKey) {
        AiServerGazeAnalysisVO response = WebClient.builder()
                .baseUrl(BASE_URL)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(GAZE_ANALYSIS_URI)
                        .queryParam("interviewId", interviewId)
                        .queryParam("objectKey", objectKey)
                        .build())
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus == HttpStatus.NOT_FOUND,
                        clientResponse -> Mono.error(new AnalysisException(NOT_EXIST_INTERVIEW_VIDEO))
                )
                .onStatus(
                        httpStatus -> httpStatus == HttpStatus.BAD_REQUEST,
                        clientResponse -> Mono.error(new AnalysisException(FAILED_GAZE_ANALYSIS))
                )
                .bodyToMono(AiServerGazeAnalysisVO.class)
                .block();

        return response;

    }

}
