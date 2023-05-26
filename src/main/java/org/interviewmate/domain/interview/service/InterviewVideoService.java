package org.interviewmate.domain.interview.service;

import static org.interviewmate.global.error.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.model.dto.response.InterviewVideoFindOutDto;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.interview.repository.InterviewVideoRepository;
import org.interviewmate.domain.question.exception.QuestionException;
import org.interviewmate.domain.question.model.Question;
import org.interviewmate.domain.question.repository.QuestionRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewVideoService {

    private final InterviewRepository interviewRepository;
    private final InterviewVideoRepository interviewVideoRepository;
    private final QuestionRepository questionRepository;

    public void createInterviewVideo(Long interviewId, Long questionId, String url) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    return new QuestionException(QUESTION_NOT_FOUND);
                });

        InterviewVideo interviewVideo = InterviewVideo.builder()
                .interview(findInterview)
                .question(question)
                .url(url)
                .build();

        interviewVideoRepository.save(interviewVideo);

    }


    public InterviewVideoFindOutDto findInterviewVideo(Long interviewId) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(NOT_FOUND_DATA));

        List<InterviewVideo> interviewVideos = interviewVideoRepository.findAllByInterview(findInterview);
        List<String> urls = interviewVideos.stream()
                .map(interviewVideo -> interviewVideo.getUrl())
                .collect(Collectors.toList());

        return InterviewVideoFindOutDto.of(urls);

    }
}
