package org.interviewmate.domain.interview.service;

import org.interviewmate.domain.interview.model.dto.request.InterviewGetAnswerRequest;
import org.interviewmate.domain.interview.model.dto.request.InterviewGetBehaviorAnalysisRequest;
import org.interviewmate.domain.interview.model.dto.request.InterviewGetKeywordRequest;
import org.interviewmate.domain.interview.model.dto.request.InterviewGetQuestionRequest;
import org.interviewmate.domain.interview.model.dto.response.InterviewGetKeywordResponse;
import org.interviewmate.domain.interview.model.dto.response.InterviewGetQuestionResponse;

import java.util.List;

public interface InterviewAiService {

    //키워드 추출 요청 (포트폴리오 넣어서 키워드 받고 저장)
    public void getKeyword(InterviewGetKeywordRequest dto);

    //질문 생성 요청 (면접 생성 시 ai 서버에 질문 생성 요청 (질문 추천 요청))
    public List<InterviewGetQuestionResponse> getQuestion(InterviewGetQuestionRequest dto);

    //답변 생성 (ai 서버에 답변 결과(오디오 -> 텍스트) 요청)
    public void getAnswer(InterviewGetAnswerRequest dto);

    //행동 분석 생성
    public void getBehaviorAnalysis(InterviewGetBehaviorAnalysisRequest dto);
}
