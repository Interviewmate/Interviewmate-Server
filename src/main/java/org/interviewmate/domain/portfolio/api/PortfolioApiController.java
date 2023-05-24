package org.interviewmate.domain.portfolio.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.portfolio.model.dto.request.PortfolioGetKeywordRequestDto;
import org.interviewmate.domain.portfolio.service.PortfolioService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

@Slf4j
@Tag(name = "포트폴리오 관련 API")
@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioApiController {
    private final PortfolioService portfolioService;

    @Operation(summary = "포트폴리오 키워드 추출 요청", description = "pathVariable (Long userId) 을 이용해 포트폴리오에서 키워드를 추출합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "포트폴리오 키워드 추출 성공")
    })
    @GetMapping("/keyword/{userId}")
    public ResponseEntity<ResponseDto<String>> getPortfolioKeyword(@PathVariable("userId") Long userId) {
        portfolioService.getKeyword(new PortfolioGetKeywordRequestDto(userId));
        return ResponseUtil.SUCCESS(SUCCESS, "키워드 추출 완료");
    }

    @Operation(summary = "포트폴리오 등록", description = "S3에 포트폴리오를 업로드 한 포트폴리오에 대한 객체를 생성합니다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 식별자"),
            @Parameter(name = "objectUrl", description = "객체 Url", example = "https://interviewmate.s3.ap-northeast-2.amazonaws.com/portfolio/4/12ebfbe8-63bd-4f8c-b088-5c2255da2f63")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<String>> createPortfolio(
            @PathVariable Long userId,
            @RequestParam String objectUrl
    ) {
        portfolioService.createPortfolio(userId, objectUrl);
        return ResponseUtil.SUCCESS(SUCCESS, "포트폴리오 등록 완료");
    }

}
