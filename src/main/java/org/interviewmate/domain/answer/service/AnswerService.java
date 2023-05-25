package org.interviewmate.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.answer.exception.AnswerException;
import org.interviewmate.domain.answer.model.Answer;
import org.interviewmate.domain.answer.model.dto.AnswerInfoDto;
import org.interviewmate.domain.answer.model.dto.response.AnswerGetListResponseDto;
import org.interviewmate.domain.answer.repository.AnswerRepository;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final InterviewRepository interviewRepository;

    public AnswerGetListResponseDto getAnswer(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new AnswerException(ErrorCode.INTERVIEW_NOT_FOUND));
        List<AnswerInfoDto> answerInfoDtoList = answerRepository.findAllByInterview(interview).stream().map(answer -> new AnswerInfoDto(answer)).collect(Collectors.toList());

        return new AnswerGetListResponseDto(answerInfoDtoList);

    }
}
