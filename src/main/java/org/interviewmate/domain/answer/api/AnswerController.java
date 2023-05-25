package org.interviewmate.domain.answer.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.answer.model.dto.response.AnswerGetListResponseDto;
import org.interviewmate.domain.answer.service.AnswerService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

@Slf4j
@Tag(name = "Answer", description = "답변 관련 API")
@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @Operation(summary = "인터뷰 답변 분석 조회", description = "interviewId로 해당 인터뷰의 답변을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "면접 생성 성공")
    })
    @GetMapping("/{interviewId}")
    public ResponseEntity<ResponseDto<AnswerGetListResponseDto>> getAnswerList(@Valid @PathVariable("interviewId") Long interviewId) {
        AnswerGetListResponseDto answer = answerService.getAnswer(interviewId);
        return ResponseUtil.SUCCESS(SUCCESS, answer);
    }
}
