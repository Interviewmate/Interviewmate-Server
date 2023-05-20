package org.interviewmate.domain.analysis.service;

import static org.interviewmate.global.error.ErrorCode.FAILED_BEHAVIOR_ANALYSIS;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_INTERVIEW_VIDEO;
import static org.interviewmate.global.error.ErrorCode.NOT_FOUND_DATA;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.analysis.exception.AnalysisException;
import org.interviewmate.domain.analysis.model.BehaviorAnalysis;
import org.interviewmate.domain.analysis.model.vo.AiServerBehaviorAnalysisVO;
import org.interviewmate.domain.analysis.repository.BehaviorAnalysisRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class BehaviorAnalysisService {

    @Value("{$ai-model.analysis.behavior}")
    private String BEHAVIOR_ANALYSIS_URI = "/behavior_analysis";

    private final InterviewRepository interviewRepository;
    private final BehaviorAnalysisRepository behaviorAnalysisRepository;
    public void createBehaviorAnalysis(Long interviewId, String objectKey) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        AiServerBehaviorAnalysisVO response = executeBehaviorAnalysis(findInterview.getInterId(), objectKey);

        List<BehaviorAnalysis> behaviorAnalyses = response.getAiServerBehaviorAnalysisResults().stream()
                .map(result -> BehaviorAnalysis.builder()
                                .start(result.getInSec())
                                .end(result.getOutSec())
                                .duringTIme(result.getDuringSec())
                                .build())
                .collect(Collectors.toList());

        behaviorAnalysisRepository.saveAll(behaviorAnalyses);

    }

    private AiServerBehaviorAnalysisVO executeBehaviorAnalysis(Long interviewId, String objectKey) {

        AiServerBehaviorAnalysisVO response = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder.path(BEHAVIOR_ANALYSIS_URI)
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
                        clientResponse -> Mono.error(new AnalysisException(FAILED_BEHAVIOR_ANALYSIS))
                )
                .bodyToMono(AiServerBehaviorAnalysisVO.class)
                .block();

        return response;

    }
}
