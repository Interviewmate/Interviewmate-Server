package org.interviewmate.domain.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.portfolio.exception.PortfolioException;
import org.interviewmate.domain.portfolio.model.Portfolio;
import org.interviewmate.domain.portfolio.model.dto.request.PortfolioGetKeywordRequestDto;
import org.interviewmate.domain.portfolio.model.dto.response.PortfolioAiServerResponseDto;
import org.interviewmate.domain.portfolio.repository.PortfolioRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // todo: createPortfolio는 debug 위한 메서드. s3 service에서 구현되면 삭제해야 함
    public void createPortfolio(Long userId, String url) {
        User user = userRepository.findById(userId).orElseThrow(() -> new PortfolioException(ErrorCode.NOT_EXIST_USER));


        Portfolio portfolio = portfolioRepository.save(Portfolio.builder()
                .url(url)
                .user(user)
                .build());
        portfolioRepository.save(portfolio);
    }

    public void getKeyword(PortfolioGetKeywordRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new PortfolioException(ErrorCode.NOT_EXIST_USER));
        Portfolio portfolio = portfolioRepository.findByUser(user).orElseThrow(() -> new PortfolioException(ErrorCode.PORTFOLIO_NOT_FOUND));

        List<String> keywords = sendRequestToAiServer(portfolio.getUrl(), dto.getUserId());
        portfolio.setKeyword(keywords);

        log.info("portfolio.getkeyword: {}",portfolio.getKeywords());

        portfolioRepository.save(portfolio);
    }
    
    private List<String> sendRequestToAiServer(String url, Long userId){ //ai 서버로 키워드 추출 요청
        //ai 서버에 get 요청
        PortfolioAiServerResponseDto response = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path("localhost:5000/keyword")
                        .queryParam("url", url)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToMono(PortfolioAiServerResponseDto.class)
                .block();

        //todo : flask에서 statusCode 넣어주면 각 code별 에러처리

        //string -> list
        List<String> keywords = Arrays.asList(response.getKeywords().split(" "));

        log.info("포트폴리오 키워드: {}", keywords);

        return keywords;
    }
}
