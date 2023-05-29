package org.interviewmate.domain.analysis.service;

import static org.interviewmate.domain.interview.model.AnalysisStatus.DONE;
import static org.interviewmate.domain.interview.model.AnalysisStatus.NOT_DONE;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_USER;
import static org.interviewmate.global.error.ErrorCode.NOT_FOUND_DATA;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.analysis.model.AnalysisData;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.analysis.model.dto.response.BehaviorAnalysisFindOutDto;
import org.interviewmate.domain.analysis.model.dto.response.ComprehensiveAnalysisProcessOutDto;
import org.interviewmate.domain.analysis.model.vo.AnalysisScoreVO;
import org.interviewmate.domain.analysis.model.keywordDistribution;
import org.interviewmate.domain.answer.model.Answer;
import org.interviewmate.domain.answer.repository.AnswerRepository;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.interview.repository.InterviewVideoRepository;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnalysisService {

    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewVideoRepository interviewVideoRepository;
    private final AnswerRepository answerRepository;

    /**
     * 종합 분석
     * 1. 모의 면접 검색
     * 2. 키워드 분포도 생성
     * 3. 시선/자세 분석 별로 점수 수집
     * 4. 모의 면접 평균 점수 계산
     */
    public ComprehensiveAnalysisProcessOutDto processComprehensiveAnalysis(Long userId) {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        List<Interview> findInterview = interviewRepository.findAllByUserAndAnalysisStatus(findUser, DONE);

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
                .forEach(k -> {
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
     * 행동 분석 조회
     */
    public List<BehaviorAnalysisFindOutDto> findBehaviorAnalysis(Long interviewId) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        int answerNumber = answerRepository.findAllByInterview(findInterview).size();

        GazeAnalysis gazeAnalysis = findInterview.getGazeAnalysis();
        PoseAnalysis poseAnalysis = findInterview.getPoseAnalysis();

        double gazeScore = 100.0;
        gazeScore -= gazeAnalysis.getGazeAnalysisData()
                .stream()
                .mapToDouble(
                        gazeAnalysisData ->
                                getScore(
                                        gazeAnalysisData.getDuringTime(),
                                        findInterview.getVideoDuration(),
                                        answerNumber
                                )
                ).sum();

        double poseScore = 100.0;
        poseScore -= poseAnalysis.getPoseAnalysisData()
                .stream()
                .mapToDouble(
                        poseAnalysisData ->
                                getScore(
                                        poseAnalysisData.getDuringTime(),
                                        findInterview.getVideoDuration(),
                                        answerNumber
                                )
                ).sum();

        findInterview.setScore(gazeScore, poseScore);
        findInterview.setAnalysisStatus(DONE);
        interviewRepository.save(findInterview);

        List<InterviewVideo> interviewVideos = interviewVideoRepository.findAllByInterview(findInterview);

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

    private Double getScore(Double duringTime, Double videoTime, int answerNumber) {
        return (duringTime / videoTime) * 100 / answerNumber;
    }

    public String isAnalysisDone(Long interviewId) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        if (findInterview.getAnalysisStatus() == NOT_DONE) {
            return "false";
        }

        return "true";

    }
}
