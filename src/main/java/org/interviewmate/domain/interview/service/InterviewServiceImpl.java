package org.interviewmate.domain.interview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.debug.user.UserDebugService;
import org.interviewmate.domain.analysis.model.BehaviorAnalysis;
import org.interviewmate.domain.analysis.service.BehaviorAnalysisService;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.dto.request.InterviewCreateRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewDeleteRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewFindDailyRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewFindMonthlyRequestDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewCreateResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewFindDailyResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewFindMonthlyResponseDto;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.domain.user.service.UserService;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final InterviewRepository interviewRepository;
    private final BehaviorAnalysisService behaviorAnalysisService;
    private final UserRepository userRepository;

    /**
     * 면접 생성
     * @param dto
     * @return 생성된 interview id
     */
    @Override
    public InterviewCreateResponseDto createInterview(InterviewCreateRequestDto dto) {

        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new InterviewException(ErrorCode.NOT_EXIST_USER));

        Interview interview = Interview.builder()
                .user(user)
                .build();

        Interview save = interviewRepository.save(interview);
        return InterviewCreateResponseDto.of(save);
    }

    /**
     * 면접 삭제
     * @param dto
     */
    @Override
    public void deleteInterview(InterviewDeleteRequestDto dto) {
        Interview interview = interviewRepository.findById(dto.getInterId()).orElseThrow(() -> new InterviewException(ErrorCode.INTERVIEW_NOT_FOUND));

        interviewRepository.delete(interview);
    }

    /**
     * 월별 면접 조회
     * @param dto
     * @return 면접 있는 날짜 리스트
     */
    @Override
    public InterviewFindMonthlyResponseDto findMonthlyInterview(InterviewFindMonthlyRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new InterviewException(ErrorCode.NOT_EXIST_USER));
        int year = dto.getYearMonth().getYear();
        int month = dto.getYearMonth().getMonthValue();
        int lastDay = dto.getYearMonth().lengthOfMonth();

        InterviewFindMonthlyResponseDto responseDto = new InterviewFindMonthlyResponseDto();
        for (int i = 1; i <= lastDay; i++) {
            if (interviewRepository.existsByUserAndCreatedAtBetween(user, LocalDateTime.of(year, month, i, 0, 0,0), LocalDateTime.of(year, month, i, 23, 59,59))) {
                log.info("add responseDto: {}", LocalDate.of(year, month, i));
                responseDto.addDateList(i);
            }
        }
        responseDto.setCount(responseDto.getDateList().size());
        return responseDto;
    }

    @Override
    public List<InterviewFindDailyResponseDto> findDailyInterview(InterviewFindDailyRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new InterviewException(ErrorCode.NOT_EXIST_USER));

        LocalDateTime startDateTime = LocalDateTime.of(dto.getDate(), LocalTime.of(0, 0, 0));
        LocalDateTime endDateTime = LocalDateTime.of(dto.getDate(), LocalTime.of(23, 59, 59));
        List<Interview> interviewList = interviewRepository.findByUserAndCreatedAtBetween(user, startDateTime, endDateTime);

        List<InterviewFindDailyResponseDto> responseDtoList = new ArrayList<>();
        for (Interview in : interviewList) {
            responseDtoList.add(InterviewFindDailyResponseDto.of(in));
        }

        return responseDtoList;
    }

    public long count() {
        return interviewRepository.count();
    }
}
