package org.interviewmate.domain.interview.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.interview.exception.InterviewException;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.domain.interview.model.dto.response.InterviewVideoFindOutDto;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.interview.repository.InterviewVideoRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewVideoService {

    private final InterviewRepository interviewRepository;
    private final InterviewVideoRepository interviewVideoRepository;

    public void createInterviewVideo(Long interviewId, String url) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(ErrorCode.NOT_FOUND_DATA));

        InterviewVideo interviewVideo = InterviewVideo.builder()
                .interview(findInterview)
                .url(url)
                .build();

        interviewVideoRepository.save(interviewVideo);

    }


    public InterviewVideoFindOutDto findInterviewVideo(Long interviewId) {

        Interview findInterview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(ErrorCode.NOT_FOUND_DATA));

        List<InterviewVideo> interviewVideos = interviewVideoRepository.findAllByInterview(findInterview);
        List<String> urls = interviewVideos.stream()
                .map(interviewVideo -> interviewVideo.getUrl())
                .collect(Collectors.toList());

        return InterviewVideoFindOutDto.of(urls);

    }
}
