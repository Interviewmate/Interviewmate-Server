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
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.dto.response.BehaviorAnalysisFindOutDto;
import org.interviewmate.domain.analysis.model.vo.AiServerBehaviorAnalysisVO;
import org.interviewmate.domain.analysis.model.vo.AnalysisDataVO;
import org.interviewmate.domain.analysis.repository.PoseAnalysisRepository;
import org.interviewmate.domain.analysis.repository.GazeAnalysisRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.model.vo.InterviewVideoVo;
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

    @Value("${ai-model.url.analysis.behavior}")
    private String behaviorAnalysisUri;

    public void processComprehensiveAnalysis(Long userId) {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        List<Interview> findInterview = interviewRepository.findAllByUser(findUser);

        List<PoseAnalysis> behaviorAnalyses = new ArrayList<>();
        List<GazeAnalysis> gazeAnalyses = new ArrayList<>();

//        findInterview.stream()
//                .forEach(interview -> {
//                    behaviorAnalyses.add(poseAnalysisRepository.findAllByInterview(interview));
//                    gazeAnalysisRepository.findAllByInterview(interview).stream()
//                            .forEach(gazeAnalysis -> gazeAnalyses.add(gazeAnalysis));
//                });

    }

    public void processBehaviorAnalysis(Long interviewId, String objectKey) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        String Url = "https://interviewmate.s3.ap-northeast-2.amazonaws.com/" + objectKey;
        InterviewVideo findVideo = interviewVideoRepository.findByUrl(Url);

        log.info("------------분석 요청------------");
        AiServerBehaviorAnalysisVO response = executeBehaviorAnalysis(objectKey);
        log.info("------------분석 완료------------");

        log.info(String.valueOf(response.getVideoDuration()));
        findInterview.setVideoDuration(String.valueOf(response.getVideoDuration()));
        interviewRepository.save(findInterview);

        List<PoseAnalysis> poseAnalyses = response.getPoseAnalysisResults().getAnalysisData().stream()
                .map(
                        data -> PoseAnalysis.builder()
                                .interviewVideo(findVideo)
                                .interview(findInterview)
                                .startTime(String.valueOf(data.getStartSec()))
                                .endTime(String.valueOf(data.getEndSec()))
                                .duringTime(String.valueOf(data.getDuringSec()))
                                .build())
                .collect(Collectors.toList());
        poseAnalysisRepository.saveAll(poseAnalyses);

        List<GazeAnalysis> gazeAnalyses = response.getGazeAnalysisResults().getAnalysisData().stream()
                .map(
                        data -> GazeAnalysis.builder()
                                .interviewVideo(findVideo)
                                .interview(findInterview)
                                .startTime(String.valueOf(data.getStartSec()))
                                .endTime(String.valueOf(data.getEndSec()))
                                .duringTime(String.valueOf(data.getDuringSec()))
                                .build())
                .collect(Collectors.toList());
        gazeAnalysisRepository.saveAll(gazeAnalyses);

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

        List<InterviewVideo> interviewVideos = interviewVideoRepository.findAllByInterview(findInterview);

//        List<AnalysisData> gazeAnalyses = gazeAnalysisRepository.findAllByInterview(findInterview).stream()
//                .map(gazeAnalysis -> AnalysisData.builder()
//                        .startSec(Double.valueOf(gazeAnalysis.getStartTime()))
//                        .endSec(Double.valueOf(gazeAnalysis.getEndTime()))
//                        .duringSec(Double.valueOf(gazeAnalysis.getDuringTime()))
//                        .build())
//                .collect(Collectors.toList());
//
//        List<AnalysisData> poseAnalyses = poseAnalysisRepository.findAllByInterview(findInterview).stream()
//                .map(poseAnalysis -> AnalysisData.builder()
//                        .startSec(Double.valueOf(poseAnalysis.getStartTime()))
//                        .endSec(Double.valueOf(poseAnalysis.getEndTime()))
//                        .duringSec(Double.valueOf(poseAnalysis.getDuringTime()))
//                        .build())
//                .collect(Collectors.toList());

        return interviewVideos.stream()
                .map(interviewVideo -> BehaviorAnalysisFindOutDto.of(
                        interviewVideo.getUrl(),
                        interviewVideo.getQuestion().getQuestion(),
                        gazeAnalysisRepository.findAllByInterviewVideo(interviewVideo).stream()
                                .map(gazeAnalysis -> AnalysisData.builder()
                                        .startSec(Double.valueOf(gazeAnalysis.getStartTime()))
                                        .endSec(Double.valueOf(gazeAnalysis.getEndTime()))
                                        .duringSec(Double.valueOf(gazeAnalysis.getDuringTime()))
                                        .build()).collect(Collectors.toList()),
                        poseAnalysisRepository.findAllByInterviewVideo(interviewVideo).stream()
                                .map(poseAnalysis -> AnalysisData.builder()
                                        .startSec(Double.valueOf(poseAnalysis.getStartTime()))
                                        .endSec(Double.valueOf(poseAnalysis.getEndTime()))
                                        .duringSec(Double.valueOf(poseAnalysis.getDuringTime()))
                                        .build()).collect(Collectors.toList())
                )).collect(Collectors.toList());

    }
}
