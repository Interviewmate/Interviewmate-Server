package org.interviewmate.domain.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.portfolio.exception.PortfolioException;
import org.interviewmate.domain.portfolio.model.Portfolio;
import org.interviewmate.domain.portfolio.model.dto.request.PortfolioGetKeywordRequestDto;
import org.interviewmate.domain.portfolio.model.dto.response.PortfolioAiServerResponseDto;
import org.interviewmate.domain.portfolio.model.dto.response.PortfolioCheckExisitResponseDto;
import org.interviewmate.domain.portfolio.repository.PortfolioRepository;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    @Value("${ai-model.url.portfolio.keyword}")
    private String keywordUri;

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

    public PortfolioCheckExisitResponseDto isExisitPortfolio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new PortfolioException(ErrorCode.NOT_EXIST_USER));
        if (portfolioRepository.findByUser(user).isPresent()) {
            return new PortfolioCheckExisitResponseDto(true);
        }
        return new PortfolioCheckExisitResponseDto(false);
    }
    
    private List<String> sendRequestToAiServer(String url, Long userId){ //ai 서버로 키워드 추출 요청
        //ai 서버에 get 요청
        PortfolioAiServerResponseDto response = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path(keywordUri)
                        .queryParam("url", url)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus == HttpStatus.NOT_FOUND,
                        clientResponse -> Mono.error(new PortfolioException(ErrorCode.S3_PORTFOLIO_NOT_FOUNT))
                )
                .onStatus(
                        httpStatus -> httpStatus == HttpStatus.BAD_REQUEST,
                        clientResponse -> Mono.error(new PortfolioException(ErrorCode.SERVER_FAILED_CREATE_KEYWORD))
                )
                .bodyToMono(PortfolioAiServerResponseDto.class)
                .block();

        //string -> list
        List<String> keywords = Arrays.asList(response.getKeywords().split(" "));

        return keywords;
    }
}
