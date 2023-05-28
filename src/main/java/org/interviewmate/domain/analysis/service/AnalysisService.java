package org.interviewmate.domain.analysis.service;

import static org.interviewmate.global.error.ErrorCode.FAILED_BEHAVIOR_ANALYSIS;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_INTERVIEW_VIDEO;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_USER;
import static org.interviewmate.global.error.ErrorCode.NOT_FOUND_DATA;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.interviewmate.domain.analysis.model.dto.response.ComprehensiveAnalysisProcessOutDto;
import org.interviewmate.domain.analysis.model.vo.AiServerBehaviorAnalysisVO;
import org.interviewmate.domain.analysis.model.vo.AnalysisScoreVO;
import org.interviewmate.domain.analysis.model.keywordDistribution;
import org.interviewmate.domain.analysis.repository.GazeAnalysisDataRepository;
import org.interviewmate.domain.analysis.repository.PoseAnalysisDataRepository;
import org.interviewmate.domain.analysis.repository.PoseAnalysisRepository;
import org.interviewmate.domain.analysis.repository.GazeAnalysisRepository;
import org.interviewmate.domain.answer.model.Answer;
import org.interviewmate.domain.answer.repository.AnswerRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.interview.repository.InterviewVideoRepository;
import org.interviewmate.domain.question.repository.QuestionRepository;
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
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final PoseAnalysisRepository poseAnalysisRepository;
    private final GazeAnalysisRepository gazeAnalysisRepository;
    private final PoseAnalysisDataRepository poseAnalysisDataRepository;
    private final GazeAnalysisDataRepository gazeAnalysisDataRepository;

    @Value("${ai-model.url.analysis.behavior}")
    private String behaviorAnalysisUri;

    /**
     * 종합 분석 1. 모의 면접 검색 2. 시선/자세 분석 별로 점수 수집 3. 모의 면접 평균 점수 계산
     */
    public ComprehensiveAnalysisProcessOutDto processComprehensiveAnalysis(Long userId) {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        List<Interview> findInterview = interviewRepository.findAllByUser(findUser);

        List<keywordDistribution> keywordDistributions = getKeywordDistribution(findInterview);
        List<AnalysisScoreVO> gazeScore = getGazeScore(findInterview);
        List<AnalysisScoreVO> poseScore = getPoseScore(findInterview);
        Long averageInterviewScore = getAverageInterviewScore(findInterview);

        return ComprehensiveAnalysisProcessOutDto.of(averageInterviewScore, gazeScore, poseScore, keywordDistributions);

    }

    private List<keywordDistribution> getKeywordDistribution(List<Interview> findInterview) {

        List<Answer> answers = new ArrayList<>();
        findInterview.stream()
                .forEach(interview -> {
                    answers.addAll(answerRepository.findAllByInterview(interview));
                });

        List<String> keywords = answers.stream()
                .map(answer -> answer.getQuestion().getKeyword())
                .collect(Collectors.toList());

        List<keywordDistribution> keywordDistributions = new ArrayList<>();
        keywords.stream()
                .forEach(
                        keyword -> {
                            if (keywordDistributions.stream().noneMatch(k -> k.getName().equals(keyword))) {

                                keywordDistributions.add(
                                        keywordDistribution.builder()
                                                .name(keyword)
                                                .count(0L)
                                                .build());
                            }
                        });

        keywordDistributions.stream()
                .forEach(k ->  {
                    keywords.stream()
                            .forEach(keyword -> {
                                if (k.getName().equals(keyword)) {
                                    k.count();
                                }
                            });
                });

        return keywordDistributions;

    }

    private static List<AnalysisScoreVO> getGazeScore(List<Interview> findInterview) {
        List<AnalysisScoreVO> gazeScore = findInterview.stream()
                .map(interview ->
                        AnalysisScoreVO.builder()
                                .interviewId(interview.getInterId())
                                .score(interview.getGazeScore())
                                .build()
                ).collect(Collectors.toList());
        return gazeScore;
    }

    private static List<AnalysisScoreVO> getPoseScore(List<Interview> findInterview) {
        List<AnalysisScoreVO> poseScore = findInterview.stream()
                .map(interview ->
                        AnalysisScoreVO.builder()
                                .interviewId(interview.getInterId())
                                .score(interview.getPoseScore())
                                .build()
                ).collect(Collectors.toList());
        return poseScore;
    }

    private Long getAverageInterviewScore(List<Interview> findInterview) {

        Long score = findInterview
                .stream()
                .mapToLong(interview -> interview.getScore())
                .sum();

        return Math.round(score / (double) findInterview.size());
    }

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

        findInterview.setVideoDuration(response.getVideoDuration());
        interviewRepository.save(findInterview);

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

        gazeAnalysisRepository.save(gazeAnalysis);
        poseAnalysisRepository.save(poseAnalysis);

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

    /**
     * 행동 분석 결과 생성
     */
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

        findInterview.setScore(gazeScore, poseScore);
        interviewRepository.save(findInterview);

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
