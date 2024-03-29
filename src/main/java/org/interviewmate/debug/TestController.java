package org.interviewmate.debug;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.debug.baseentity.TestService;
import org.interviewmate.domain.analysis.service.AnswerAnalysisService;
import org.interviewmate.domain.portfolio.service.PortfolioService;
import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.interviewmate.global.util.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Tag(name = "테스트용 컨트롤러", description = "개발하면서 필요한 debug용 Controller")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final PortfolioService portfolioService;
    private final AnswerAnalysisService answerAnalysisService;

    @GetMapping("/success")
    public ResponseEntity<ResponseDto<ArrayList>> successRepsonseTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("성공1");
        list.add("성공2");
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, list);
    }

    @GetMapping("/failure")
    public ResponseEntity<ResponseDto<ArrayList>> failResponseTest() {
        return ResponseUtil.FAILURE(ResponseCode.FAILURE_ALREADY_REPORTED, new ArrayList<>());
    }

    @GetMapping("/error")
    public void errorResponseTest() {
        throw new CustomException(ErrorCode.NOT_FOUND_DATA);
    }

    @GetMapping("/exception")
    public void exceptionResponseTest() {
        throw new TestException(ErrorCode.NOT_FOUND_DATA);
    }

    @GetMapping("/entity")
    public ResponseEntity baseEntityTest() {
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, testService.findAll());
    }

    @PostMapping("/entity")
    public ResponseEntity createBaseEntityTest(@RequestParam("name") String name) {
        testService.create(name);
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, null);
    }

    @GetMapping("/portfolio")
    public ResponseEntity<ResponseDto<String>> createPortfolio(@RequestParam("userId") Long userId,
                                                               @RequestParam("url") String url) {
        portfolioService.createPortfolio(userId, url);
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, null);
    }

    @GetMapping("/answer_analysis")
    public ResponseEntity<ResponseDto<String>> createAnswerAnalysis(@RequestParam("interviewId") Long interviewId,
                                                                    @RequestParam("questionId") Long questionId,
                                                                    @RequestParam("objectKey") String objectKey) {
        answerAnalysisService.createAnswerAnalysis(interviewId, questionId, objectKey);
        return ResponseUtil.SUCCESS(ResponseCode.SUCCESS, "");
    }
}
