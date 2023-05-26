package org.interviewmate.domain.analysis.service;

import static org.interviewmate.global.error.ErrorCode.FAILED_BEHAVIOR_ANALYSIS;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_INTERVIEW_VIDEO;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_USER;
import static org.interviewmate.global.error.ErrorCode.NOT_FOUND_DATA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.analysis.exception.AnalysisException;
import org.interviewmate.domain.analysis.model.AnalysisData;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.GazeAnalysisData;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.analysis.model.PoseAnalysisData;
import org.interviewmate.domain.analysis.model.dto.response.BehaviorAnalysisFindOutDto;
import org.interviewmate.domain.analysis.model.vo.AiServerBehaviorAnalysisVO;
import org.interviewmate.domain.analysis.repository.GazeAnalysisDataRepository;
import org.interviewmate.domain.analysis.repository.PoseAnalysisDataRepository;
import org.interviewmate.domain.analysis.repository.PoseAnalysisRepository;
import org.interviewmate.domain.analysis.repository.GazeAnalysisRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.interview.repository.InterviewVideoRepository;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
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
public class AnalysisService {

    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewVideoRepository interviewVideoRepository;
    private final PoseAnalysisRepository poseAnalysisRepository;
    private final GazeAnalysisRepository gazeAnalysisRepository;
    private final PoseAnalysisDataRepository poseAnalysisDataRepository;
    private final GazeAnalysisDataRepository gazeAnalysisDataRepository;

    @Value("${ai-model.url.analysis.behavior}")
    private String behaviorAnalysisUri;

    public void processComprehensiveAnalysis(Long userId) {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        List<Interview> findInterview = interviewRepository.findAllByUser(findUser);

        List<GazeAnalysisData> gazeAnalyses = new ArrayList<>();

//        List<PoseAnalysisData> poseAnalyses =


    }

    public void processBehaviorAnalysis(Long interviewId, String objectKey) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        String Url = "https://interviewmate.s3.ap-northeast-2.amazonaws.com/" + objectKey;
        InterviewVideo findVideo = interviewVideoRepository.findByUrl(Url);

        log.info("------------분석 요청------------");
        AiServerBehaviorAnalysisVO response = executeBehaviorAnalysis(objectKey);
        log.info("------------분석 완료------------");

        PoseAnalysis poseAnalysis = PoseAnalysis.builder().build();
        GazeAnalysis gazeAnalysis = GazeAnalysis.builder().build();

        log.info(String.valueOf(response.getVideoDuration()));
        findInterview.setVideoDuration(response.getVideoDuration());
        interviewRepository.save(findInterview);

        List<PoseAnalysisData> poseAnalyses = response.getPoseAnalysisResults().getAnalysisData().stream()
                .map(
                        data -> PoseAnalysisData.builder()
                                .interviewVideo(findVideo)
                                .startTime(data.getStartSec())
                                .endTime(data.getEndSec())
                                .duringTime(data.getDuringSec())
                                .build())
                .collect(Collectors.toList());
        poseAnalysisDataRepository.saveAll(poseAnalyses);

        List<GazeAnalysisData> gazeAnalyses = response.getGazeAnalysisResults().getAnalysisData().stream()
                .map(
                        data -> GazeAnalysisData.builder()
                                .interviewVideo(findVideo)
                                .startTime(data.getStartSec())
                                .endTime(data.getEndSec())
                                .duringTime(data.getDuringSec())
                                .build())
                .collect(Collectors.toList());
        gazeAnalysisDataRepository.saveAll(gazeAnalyses);

        poseAnalysis.setPoseAnalysisData(poseAnalyses);
        gazeAnalysis.setGazeAnalysisData(gazeAnalyses);
        poseAnalysisRepository.save(poseAnalysis);
        gazeAnalysisRepository.save(gazeAnalysis);

        findInterview.setAnalysis(poseAnalysis, gazeAnalysis);

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

    public List<BehaviorAnalysisFindOutDto> findBehaviorAnalysis(Long interviewId) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        GazeAnalysis gazeAnalysis = findInterview.getGazeAnalysis();
        PoseAnalysis poseAnalysis = findInterview.getPoseAnalysis();

        List<InterviewVideo> interviewVideos = interviewVideoRepository.findAllByInterview(findInterview);

        double gazeScore = 100.0;
        gazeScore -= gazeAnalysis.getGazeAnalysisData()
                .stream()
                .mapToDouble(
                        gazeAnalysisData ->
                                getScore(gazeAnalysisData.getDuringTime(), findInterview.getVideoDuration())
                ).sum();

        double poseScore = 100.0;
        poseScore -= poseAnalysis.getPoseAnalysisData()
                .stream()
                .mapToDouble(
                        poseAnalysisData ->
                                getScore(poseAnalysisData.getDuringTime(), findInterview.getVideoDuration())
                ).sum();

        Long score = Math.round((poseScore + gazeScore) / 2);
        findInterview.setScore(score);
        interviewRepository.save(findInterview);
        log.info(String.valueOf(score));



        return interviewVideos.stream()
                .map(interviewVideo -> BehaviorAnalysisFindOutDto.of(
                        interviewVideo.getUrl(),
                        interviewVideo.getQuestion().getQuestion(),
                        findInterview.getScore(),
                        gazeAnalysis.getGazeAnalysisData().stream()
                                .map(gazeAnalysisData -> AnalysisData.builder()
                                        .startSec(gazeAnalysisData.getStartTime())
                                        .endSec(gazeAnalysisData.getEndTime())
                                        .duringSec(gazeAnalysisData.getDuringTime())
                                        .build()).collect(Collectors.toList()),
                        poseAnalysis.getPoseAnalysisData().stream()
                                .map(poseAnalysisData -> AnalysisData.builder()
                                        .startSec(poseAnalysisData.getStartTime())
                                        .endSec(poseAnalysisData.getEndTime())
                                        .duringSec(poseAnalysisData.getDuringTime())
                                        .build()).collect(Collectors.toList())
                )).collect(Collectors.toList());

    }

    private Double getScore(Double duringTime, Double videoTime) {
        return (duringTime / videoTime) * 100;
    }

}
