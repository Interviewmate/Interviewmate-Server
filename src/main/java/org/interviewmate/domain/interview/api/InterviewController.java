package org.interviewmate.domain.interview.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.interview.model.dto.request.InterviewCreateRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewDeleteRequestDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewCreateResponseDto;
import org.interviewmate.domain.interview.service.InterviewServiceImpl;
import org.interviewmate.global.util.response.ResponseCode;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;

import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

@Tag(name = "Interview", description = "면접 관련 API")
@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewServiceImpl interviewService;

    @Operation(summary = "면접 생성", description = "InterviewCreateRequestDto를 이용해 면접을 생성합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "면접 생성 성공", content = @Content(schema = @Schema(implementation = InterviewCreateResponseDto.class)))
    })
    @PostMapping("/")
    public ResponseDto createInterview(@RequestBody InterviewCreateRequestDto dto) {
        InterviewCreateResponseDto interview = interviewService.createInterview(dto);
        return ResponseUtil.SUCCESS(SUCCESS, interview);
    }

    @DeleteMapping("/")
    public ResponseDto deleteInterview(@RequestBody InterviewDeleteRequestDto dto) {
        interviewService.deleteInterview(dto);
        return ResponseUtil.SUCCESS(SUCCESS, null);
    }
}
