package org.interviewmate.domain.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.portfolio.exception.PortfolioException;
import org.interviewmate.domain.portfolio.model.Portfolio;
import org.interviewmate.domain.portfolio.repository.PortfolioRepository;
import org.interviewmate.domain.question.exception.QuestionException;
import org.interviewmate.domain.question.model.dto.request.QuestionAiServerRequestDto;
import org.interviewmate.domain.question.model.dto.request.QuestionGetRequestDto;
import org.interviewmate.domain.question.model.dto.response.QuestionGetResponseDto;
import org.interviewmate.domain.question.model.dto.response.QuestionInfoDto;
import org.interviewmate.domain.question.repository.QuestionRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    @Value("${ai-model.url.question}")
    private String questionUri;

    //질문 추천(요청)
    public QuestionGetResponseDto getQuestion(QuestionGetRequestDto dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new PortfolioException(ErrorCode.NOT_EXIST_USER));
        Portfolio portfolio = portfolioRepository.findByUser(user).orElse(new Portfolio("", user));

        //userKeyword + csKeyword
        List<String> userKeyword = user.getUserKeywords().stream().map(keyword -> keyword.getKeyword().getName()).collect(Collectors.toList());
        userKeyword.addAll(dto.getCsKeyword());

        QuestionAiServerRequestDto aiServerRequestDto = QuestionAiServerRequestDto.builder()
                .questionNum(dto.getQuestionNum())
                .userKeyword(userKeyword)
                .portfolioKeyword(portfolio.getKeywords())
                .job(user.getJob().name())
                .build();

        return sendRequestToAiServer(aiServerRequestDto);
    }

    //ai 서버에 요청 보내고 응답 받기
    private QuestionGetResponseDto sendRequestToAiServer(QuestionAiServerRequestDto dto) {

        List<Long> response = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path(questionUri)
                        .queryParam("questionNum", dto.getQuestionNum())
                        .queryParam("userKeyword", String.join(",", dto.getUserKeyword()))
                        .queryParam("portfolioKeyword", String.join(",", dto.getPortfolioKeyword()))
                        .queryParam("job", dto.getJob())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Long>>() {
                })
                .block();

        List<QuestionInfoDto> questionList = response.stream()
                .map(questionId -> new QuestionInfoDto(questionRepository.findById(questionId+1).orElseThrow()))
                .collect(Collectors.toList());

        return new QuestionGetResponseDto(questionList.size(), questionList);
    }
}
