package org.interviewmate.domain.analysis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.analysis.exception.AnalysisException;
import org.interviewmate.domain.analysis.model.AIServerAnswerAnalysisResponse;
import org.interviewmate.domain.analysis.model.vo.AIServerAnswerExtractResponse;
import org.interviewmate.domain.answer.model.Answer;
import org.interviewmate.domain.answer.repository.AnswerRepository;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.repository.InterviewRepository;
import org.interviewmate.domain.question.model.Question;
import org.interviewmate.domain.question.repository.QuestionRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnswerAnalysisService {
    @Value("${ai-model.url.answer.extract}")
    private String answerExtractUrl;

    @Value("${ai-model.url.answer.analysis}")
    private String answerAnalysisUrl;
    private final AnswerRepository answerRepository;
    private final InterviewRepository interviewRepository;
    private final QuestionRepository questionRepository;

    //프론트에서 interviewId, questionId 받아서 진행
    public void createAnswerAnalysis(Long interviewId, Long questionId, String objectKey){
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new AnalysisException(ErrorCode.INTERVIEW_NOT_FOUND));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new AnalysisException(ErrorCode.QUESTION_NOT_FOUND));

        String content = sendExtractAnswerRequest(objectKey);
        AIServerAnswerAnalysisResponse analysisResponse = sendAnalysisAnswerRequest(question.getQuestion(), content, interview.getUser().getJob().name());

        String answerAnalysis = analysisResponse.getAnalysis();
        List<String> deepQuestionList = Arrays.stream(analysisResponse.getDeep_question().split("\n"))
                .map(s -> s.substring(3, s.length())).collect(Collectors.toList());

        Answer answer = Answer.builder()
                .answerAnalysis(answerAnalysis)
                .content(content)
                .interview(interview)
                .question(question)
                .deepQuestions(deepQuestionList)
                .build();
        answerRepository.save(answer);
    }

    // ai server 답변 추출 요청 - objectKey
    private String sendExtractAnswerRequest(String objectKey) {
        AIServerAnswerExtractResponse response = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path(answerExtractUrl)
                        .queryParam("object_key", objectKey)
                        .build())
                .retrieve()
                .bodyToMono(AIServerAnswerExtractResponse.class)
                .block();
        //answer 가공
        String answer = response.getAnswer().substring(9, response.getAnswer().length()-2);
        /**
         * {
         *     "answer": "{\"text\":\"존경하는 선배님과 동료들 앞에서 제 자신에게 좀 말해 주고 싶네요 연 진희로 사느라 너무 고생했고 너도 충분히 잘 해내고 있다고 멋지다 연진아 나 상 받았어 연진아 나 지금 되게 신나 마지막을 해 보고 싶었음\"}"
         * }
         */
        return answer;
    }
    // ai server 답변 분석 요청 - question, answer, user job+Engineer
    private AIServerAnswerAnalysisResponse sendAnalysisAnswerRequest(String question, String answer, String userJob) {
        String job = userJob + " Engineer";
        AIServerAnswerAnalysisResponse response = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path(answerAnalysisUrl)
                        .queryParam("question", question)
                        .queryParam("answer", answer)
                        .queryParam("job", job)
                        .build())
                .retrieve()
                .bodyToMono(AIServerAnswerAnalysisResponse.class)
                .block();
        log.info("answerAnalysis: {}", response.getAnalysis());
        return response;
    }
}
