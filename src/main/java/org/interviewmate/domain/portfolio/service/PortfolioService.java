package org.interviewmate.domain.portfolio.service;

import com.amazonaws.services.s3.AmazonS3;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final AmazonS3 amazonS3;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //프론트가 포트폴리오 올림 (이전에 올릴 s3 링크 받았음), 키워드 추출 요청 보냄 (url과 userId 넣어서)
    // + (이 방법 혹은 //S3 사용자 id로 portfolio S3 url 찾기)
    // Portfolio setUrl, url로 포트폴리오 로컬에 다운로드, ai 서버에 로컬 저장 경로 넣어서 키워드 추출 요청
    // 반환된 키워드를 Portfolio setKeywords, 로컬에서 포트폴리오 삭제

    public void getKeyword(PortfolioGetKeywordRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new PortfolioException(ErrorCode.NOT_EXIST_USER));

        List<String> keywords = sendRequestToAiServer(dto.getUrl(), dto.getUserId());

        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .url(dto.getUrl())
                .keywords(keywords)
                .build();

        portfolioRepository.save(portfolio);
    }
    
    private List<String> sendRequestToAiServer(String url, Long userId){ //ai 서버로 키워드 추출 요청
        //ai 서버에 get 요청
        PortfolioAiServerResponseDto responseDto = WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path("localhost:5000/keyword")
                        .queryParam("url", url)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToMono(PortfolioAiServerResponseDto.class)
                .block();

        //string -> list
        List<String> keywords = Arrays.asList(responseDto.getKeywords().split(" "));

        log.info("포트폴리오 키워드: {}", keywords);

        return keywords;
    }
}
