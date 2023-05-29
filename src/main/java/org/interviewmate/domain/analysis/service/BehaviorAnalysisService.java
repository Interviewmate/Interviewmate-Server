package org.interviewmate.domain.analysis.service;

import static org.interviewmate.global.error.ErrorCode.FAILED_BEHAVIOR_ANALYSIS;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_INTERVIEW_VIDEO;
import static org.interviewmate.global.error.ErrorCode.NOT_FOUND_DATA;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.analysis.exception.AnalysisException;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.GazeAnalysisData;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.analysis.model.PoseAnalysisData;
import org.interviewmate.domain.analysis.model.vo.AiServerBehaviorAnalysisVO;
import org.interviewmate.domain.analysis.repository.GazeAnalysisDataRepository;
import org.interviewmate.domain.analysis.repository.GazeAnalysisRepository;
import org.interviewmate.domain.analysis.repository.PoseAnalysisDataRepository;
import org.interviewmate.domain.analysis.repository.PoseAnalysisRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.interview.repository.InterviewVideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BehaviorAnalysisService {

    private final InterviewRepository interviewRepository;
    private final InterviewVideoRepository interviewVideoRepository;
    private final PoseAnalysisRepository poseAnalysisRepository;
    private final GazeAnalysisRepository gazeAnalysisRepository;
    private final PoseAnalysisDataRepository poseAnalysisDataRepository;
    private final GazeAnalysisDataRepository gazeAnalysisDataRepository;

    @Value("${ai-model.url.analysis.behavior}")
    private String behaviorAnalysisUri;

    /**
     * 행동 분석 요청
     */
    public void processBehaviorAnalysis(Long interviewId, String objectKey) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        GazeAnalysis gazeAnalysis = findInterview.getGazeAnalysis();
        PoseAnalysis poseAnalysis = findInterview.getPoseAnalysis();

        String Url = "https://interviewmate.s3.ap-northeast-2.amazonaws.com/" + objectKey;
        InterviewVideo findVideo = interviewVideoRepository.findByUrl(Url);

        log.info("------------분석 요청------------");
        AiServerBehaviorAnalysisVO response = executeBehaviorAnalysis(objectKey);
        log.info("------------분석 완료------------");

        savePoseAnalysisData(poseAnalysis, findVideo, response);
        saveGazeAnalysisData(gazeAnalysis, findVideo, response);

        findInterview.setVideoDuration(response.getVideoDuration());
        interviewRepository.save(findInterview);

    }


    private void savePoseAnalysisData(PoseAnalysis poseAnalysis, InterviewVideo findVideo, AiServerBehaviorAnalysisVO response) {
        List<PoseAnalysisData> poseAnalyses = response.getPoseAnalysisResults().getAnalysisData().stream()
                .map(
                        data -> PoseAnalysisData.builder()
                                .poseAnalysis(poseAnalysis)
                                .interviewVideo(findVideo)
                                .startTime(data.getStartSec())
                                .endTime(data.getEndSec())
                                .duringTime(data.getDuringSec())
                                .build())
                .collect(Collectors.toList());
        poseAnalysisDataRepository.saveAll(poseAnalyses);
        poseAnalysis.setPoseAnalysisData(poseAnalyses);
        poseAnalysisRepository.save(poseAnalysis);
    }

    private void saveGazeAnalysisData(GazeAnalysis gazeAnalysis, InterviewVideo findVideo, AiServerBehaviorAnalysisVO response) {
        List<GazeAnalysisData> gazeAnalyses = response.getGazeAnalysisResults().getAnalysisData().stream()
                .map(
                        data -> GazeAnalysisData.builder()
                                .gazeAnalysis(gazeAnalysis)
                                .interviewVideo(findVideo)
                                .startTime(data.getStartSec())
                                .endTime(data.getEndSec())
                                .duringTime(data.getDuringSec())
                                .build())
                .collect(Collectors.toList());
        gazeAnalysisDataRepository.saveAll(gazeAnalyses);
        gazeAnalysis.setGazeAnalysisData(gazeAnalyses);
        gazeAnalysisRepository.save(gazeAnalysis);
    }

    private AiServerBehaviorAnalysisVO executeBehaviorAnalysis(String objectKey) {

        return WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder.path(behaviorAnalysisUri)
                        .queryParam("object_key", objectKey)
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

    }


}
