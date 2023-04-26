package org.interviewmate.domain.interview.service;

import org.interviewmate.domain.interview.model.dto.request.InterviewGetQuestionRequest;
import org.interviewmate.domain.interview.model.dto.response.InterviewGetQuestionResponse;

import java.util.List;

public interface InterviewAiService {
    //질문 생성 요청 (면접 생성 시 ai 서버에 질문 생성 요청)
    public List<InterviewGetQuestionResponse> getQuestion(InterviewGetQuestionRequest dto);

    //답변 생성 (ai 서버에 답변 결과(오디오 -> 텍스트) 요청)

    //행동 분석 생성
}
