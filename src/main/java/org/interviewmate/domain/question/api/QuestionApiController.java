package org.interviewmate.domain.question.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.question.model.dto.request.QuestionGetRequestDto;
import org.interviewmate.domain.question.model.dto.response.QuestionGetResponseDto;
import org.interviewmate.domain.question.service.QuestionService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

@Tag(name = "질문 관련 API")
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionApiController {
    private final QuestionService questionService;

    @Operation(summary = "면접 질문 요청", description = "면접 시작 전 질문을 요청합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "면접 질문 추천 성공")
    })
    @Parameters({
            @Parameter(name = "userId", description = "user의 id", example = "1"),
            @Parameter(name = "questionNum", description = "yyyy-MM 형식의 조회하고자 하는 year, month", example = "2023-05"),
            @Parameter(name = "csKeyword", description = "사용자가 면접 전 선택한 cs 키워드 리스트", example = "[ALGORITHM, NETWORK]")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<QuestionGetResponseDto>> getQuestion(@PathVariable("userId") Long userId,
                                                                                 @RequestParam("questionNum") Integer questionNum,
                                                                                 @RequestParam("csKeyword") List<String> csKeyword){
        return ResponseUtil.SUCCESS(SUCCESS, questionService.getQuestion(new QuestionGetRequestDto(userId, questionNum, csKeyword)));
    }

}
