package org.interviewmate.domain.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.portfolio.exception.PortfolioException;
import org.interviewmate.domain.portfolio.model.Portfolio;
import org.interviewmate.domain.portfolio.repository.PortfolioRepository;
import org.interviewmate.domain.question.model.Question;
import org.interviewmate.domain.question.model.dto.request.QuestionAiServerRequestDto;
import org.interviewmate.domain.question.model.dto.request.QuestionGetRequestDto;
import org.interviewmate.domain.question.model.dto.response.QuestionCreateResponseDto;
import org.interviewmate.domain.question.model.dto.response.QuestionGetResponseDto;
import org.interviewmate.domain.question.model.dto.response.QuestionInfoDto;
import org.interviewmate.domain.question.repository.QuestionRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Value("${ai-model.url.create_question}")
    private String createQuestionUri;

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

        List<QuestionInfoDto> portfolioQuestionList = new ArrayList<>();
        if(!portfolio.getKeywords().isEmpty()){
            portfolioQuestionList = sendCreateQuestionRequestToAiServer(portfolio.getKeywords());
        }

        if (portfolioQuestionList.isEmpty()) {
            aiServerRequestDto.setQuestionNum(10);
        } else aiServerRequestDto.setQuestionNum(8);

        List<QuestionInfoDto> questionList = sendRequestToAiServer(aiServerRequestDto);
        portfolioQuestionList.addAll(questionList);
//        questionList.addAll(portfolioQuestionList);
        return new QuestionGetResponseDto(portfolioQuestionList.size(), portfolioQuestionList);
    }

    //ai 서버에 요청 보내고 응답 받기
    private List<QuestionInfoDto> sendRequestToAiServer(QuestionAiServerRequestDto dto) {

//        List<Long> response = WebClient.create().get()
//                .uri(uriBuilder -> uriBuilder
//                        .path(questionUri)
//                        .queryParam("questionNum", dto.getQuestionNum())
//                        .queryParam("userKeyword", String.join(",", dto.getUserKeyword()))
//                        .queryParam("portfolioKeyword", String.join(",", dto.getPortfolioKeyword()))
//                        .queryParam("job", dto.getJob())
//                        .build())
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<Long>>() {
//                })
//                .block();
        List<Long> response = new ArrayList<>();
        response = Arrays.asList(1047L, 1916L, 1893L, 1957L, 1958L, 492L, 696L, 703L, 691L, 697L);

        if (dto.getQuestionNum() == 8) {
            response = Arrays.asList(1047L, 1916L, 1893L, 1957L, 1958L, 492L, 696L, 703L);
        }

        List<QuestionInfoDto> questionInfoDtoList = response.stream()
                .map(questionId -> new QuestionInfoDto(questionRepository.findById(questionId).orElse(null)))
                .collect(Collectors.toList());
        questionInfoDtoList.removeAll(Collections.singletonList(null));

        return questionInfoDtoList;
    }

    //ai 서버에 포트폴리오 질문 생성 요청 보내기
    private List<QuestionInfoDto> sendCreateQuestionRequestToAiServer(List<String> portfolioKeyword) {
        List<QuestionInfoDto> questionInfoDtoList = new ArrayList<>();

//        List<QuestionCreateResponseDto> questionList = WebClient.create().get()
//                .uri(uriBuilder -> uriBuilder
//                        .path(createQuestionUri)
//                        .queryParam("portfolioKeyword", String.join(",", portfolioKeyword))
//                        .build())
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<QuestionCreateResponseDto>>() {
//                })
//                .block();
        List<QuestionCreateResponseDto> questionList = new ArrayList<>();
        questionList.add(new QuestionCreateResponseDto("포트폴리오에 소개된 그래프 기술은 어떤 목적으로 사용되었나요? 어떤 결과를 제공하고, 어떤 데이터 스택과 기술을 사용하여 구현했나요?",
                "포트폴리오에 소개된 그래프 기술은 데이터 시각화 목적으로 사용되었습니다. 이 기술은 복잡한 데이터를 시각적으로 보여주어 데이터 분석 및 결정을 용이하게 합니다. 이 그래프 기술은 라이브러리 D3.js를 사용하여 구현되었으며, 데이터는 CSV 형식으로 구성되어 있습니다. 데이터는 Node.js 서버에서 처리되었으며, 클라이언트 측에서는 HTML, CSS 및 JavaScript를 사용하여 웹 페이지에 표시되었습니다. 이 기술은 데이터 기반 의사 결정을 지원하기 위해 사용되며, 데이터 요약, 추세, 분포 등을 시각화하여 더 나은 이해를 제공합니다.",
                "PORTFOLIO"));

        questionList.add(new QuestionCreateResponseDto("포트폴리오에 언급된 RETROFIT 기술을 활용하여 어떤 기능을 제공했나요? 해당 기술을 선택한 이유와 기능의 장점에 대해 이야기해주세요.",
                "저희 프로젝트에서 RETROFIT 기술을 활용하여 RESTful API에 접근했습니다. 이 기술을 선택한 이유는 먼저, 해당 프로젝트에서 REST API를 구현하는 데 가장 효과적인 기술 중 하나이기 때문입니다. 그리고 RETROFIT은 즉시 사용 가능하며 코드 작성이 효율적이며 의식적인 API 구성을 지원하여 개발자가 더욱 쉽게 개발할 수 있습니다. 또한 RETROFIT 기술을 사용하여 기본적인 HTTP 요청 및 응답을 처리하는 것 이상의 기능을 제공할 수 있었습니다. 예를 들어, RETROFIT은 데이터 변환, 캐싱, 스레드 관리, 오류 처리 및 매개 변수와 함께 요청을 보내는 것과 같은 기능을 제공합니다. 이러한 기능은 개발자가 코드 작성 시간을 줄이고 코드 유지 보수성을 높이는 데 매우 유용합니다. 마지막으로 RETROFIT은 자주 업데이트되는 풍부한 문서와 지속적인 개선이 지원되는 라이브러리입니다. 따라서 우리 프로젝트에서 RETROFIT을 사용하여 REST API와 상호 작용하는 것은 우리에게 큰 이점을 제공했습니다.",
                "PORTFOLIO"));

        if (questionList.isEmpty()) {
            return new ArrayList<>();
        }

        for (QuestionCreateResponseDto question : questionList) {
            Question buildQuestion = Question.builder()
                    .question(question.getQuestion())
                    .bestAnswer(question.getAnswer())
                    .keyword(question.getKeyword())
                    .questionToken(new ArrayList<>())
                    .build();
            questionRepository.save(buildQuestion);
            questionInfoDtoList.add(new QuestionInfoDto(buildQuestion));
        }
        return questionInfoDtoList;
    }
}
