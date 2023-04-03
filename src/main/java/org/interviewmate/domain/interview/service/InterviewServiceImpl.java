package org.interviewmate.domain.interview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.debug.user.UserDebugService;
import org.interviewmate.domain.behavior.model.BehaviorAnalysis;
import org.interviewmate.domain.behavior.service.BehaviorAnalysisService;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.dto.request.InterviewCreateRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewDeleteRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewGetMonthlyRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewGetQuestionRequest;
import org.interviewmate.domain.interview.model.dto.response.InterviewCreateResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewGetQuestionResponse;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.service.UserService;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{
    private final InterviewRepository interviewRepository;
    private final UserDebugService userDebugService;
    private final BehaviorAnalysisService behaviorAnalysisService;
    private final UserService userService;

    /**
     * 면접 생성
     * @param dto
     * @return 생성된 interview id
     */
    @Override
    public InterviewCreateResponseDto createInterview(InterviewCreateRequestDto dto) {
        User user = userDebugService.findUser(dto.getUserId());
        log.info("userId: {} userEmail: {} userPassword: {}", user.getUserId(), user.getEmail(),user.getPassword());
        BehaviorAnalysis behaviorAnalysis = behaviorAnalysisService.createBehaviorAnalysis();
        Interview interview = Interview.builder()
                .user(user)
                .behaviorAnalysis(behaviorAnalysis)
                .build();

        Interview save = interviewRepository.save(interview);
        return InterviewCreateResponseDto.of(save);
    }

    @Override
    public void deleteInterview(InterviewDeleteRequestDto dto) {
        Interview interview = interviewRepository.findById(dto.getInterId()).orElseThrow(() -> new InterviewException(ErrorCode.INTERVIEW_NOT_FOUND));

        interviewRepository.delete(interview);
    }

    @Override
    public void getMonthlyInterview(InterviewGetMonthlyRequestDto dto) {
        YearMonth month = YearMonth.from(dto.getDate());
        LocalDate firstDate = month.atDay(1);
        LocalDate lastDate = month.atEndOfMonth();

        interviewRepository.findByUserIdAndCreatedAtBetween(firstDate, lastDate);
    }

    public long count() {
        return interviewRepository.count();
    }
}
