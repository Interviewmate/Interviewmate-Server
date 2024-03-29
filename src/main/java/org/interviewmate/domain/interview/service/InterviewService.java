package org.interviewmate.domain.interview.service;

import org.interviewmate.domain.interview.model.dto.request.InterviewCreateRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewDeleteRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewFindDailyRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewFindMonthlyRequestDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewCreateResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewFindDailyResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewFindMonthlyResponseDto;

import java.util.List;

public interface InterviewService {

    //면접 생성
    public InterviewCreateResponseDto createInterview(InterviewCreateRequestDto dto);

    //면접 삭제
    public void deleteInterview(InterviewDeleteRequestDto dto);

    //월별 면접 조회
    public InterviewFindMonthlyResponseDto findMonthlyInterview(InterviewFindMonthlyRequestDto dto);

    //일별 면접 조회
    public List<InterviewFindDailyResponseDto> findDailyInterview(InterviewFindDailyRequestDto dto);
    //회차별 면접 조회 - 행동 분석
    //회차별 면접 조회 - 답변 분석
    //종합 분석 조회

}
