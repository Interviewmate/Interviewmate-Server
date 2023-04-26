package org.interviewmate.domain.interview.service;

import lombok.RequiredArgsConstructor;
import org.interviewmate.debug.user.UserDebugService;
import org.interviewmate.domain.behavior.service.BehaviorAnalysisService;
import org.interviewmate.domain.interview.model.dto.request.InterviewGetQuestionRequest;
import org.interviewmate.domain.interview.model.dto.response.InterviewGetQuestionResponse;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewAiServiceImpl implements InterviewAiService{
    private final InterviewRepository interviewRepository;
    private final UserDebugService userService;
    private final BehaviorAnalysisService behaviorAnalysisService;

    /**
     * 질문 생성 요청 (ai 서버에)
     * @param dto
     * @return 생성된 질문 list
     */
    @Override
    public List<InterviewGetQuestionResponse> getQuestion(InterviewGetQuestionRequest dto) {

        return null;
    }

}
