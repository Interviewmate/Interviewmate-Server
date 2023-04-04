package org.interviewmate.domain.interview.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.interview.model.dto.request.InterviewCreateRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewDeleteRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewFindDailyRequestDto;
import org.interviewmate.domain.interview.model.dto.request.InterviewFindMonthlyRequestDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewCreateResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewFindDailyResponseDto;
import org.interviewmate.domain.interview.model.dto.response.InterviewFindMonthlyResponseDto;
import org.interviewmate.domain.interview.service.InterviewServiceImpl;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.interviewmate.global.util.response.ResponseCode.*;

@Slf4j
@Tag(name = "Interview", description = "면접 관련 API")
@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewServiceImpl interviewService;

    @Operation(summary = "면접 생성", description = "InterviewCreateRequestDto를 이용해 면접을 생성합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "면접 생성 성공", content = @Content(schema = @Schema(implementation = InterviewCreateResponseDto.class)))
    })
    @PostMapping("/")
    public ResponseDto createInterview(@RequestBody InterviewCreateRequestDto dto) {
        InterviewCreateResponseDto interview = interviewService.createInterview(dto);
        return ResponseUtil.SUCCESS(CREATED, interview);
    }

    @Operation(summary = "면접 삭제", description = "InterviewDeleteRequestDto를 이용해 면접을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "면접 삭제 성공")
    })
    @DeleteMapping("/")
    public ResponseDto deleteInterview(@RequestBody InterviewDeleteRequestDto dto) {
        interviewService.deleteInterview(dto);
        return ResponseUtil.SUCCESS(DELETED, null);
    }

    @Operation(summary = "월별 면접 조회", description = "Request parameter를 이용해 면접을 생성합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "월별 면접 조회 성공", content = @Content(schema = @Schema(implementation = InterviewFindMonthlyResponseDto.class)))
    })
    @GetMapping("/month")
    public ResponseDto findMonthlyInterview(@RequestParam("userId") Long userId, @RequestParam("yearMonth") YearMonth yearMonth) {
        InterviewFindMonthlyRequestDto dto = new InterviewFindMonthlyRequestDto(userId, yearMonth);
        InterviewFindMonthlyResponseDto monthlyInterview = interviewService.findMonthlyInterview(dto);
        return ResponseUtil.SUCCESS(SUCCESS, monthlyInterview);
    }

    @Operation(summary = "일별 면접 조회", description = "Request parameter를 이용해 면접을 생성합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "일별 면접 조회 성공", content = @Content(schema = @Schema(implementation = InterviewFindDailyResponseDto.class)))
    })
    @GetMapping("/day")
    public ResponseDto findDailyInterview(@RequestParam("userId") Long userId,
                                          @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        InterviewFindDailyRequestDto dto = new InterviewFindDailyRequestDto(userId, date);
        List<InterviewFindDailyResponseDto> dailyInterviewList = interviewService.findDailyInterview(dto);
        return ResponseUtil.SUCCESS(SUCCESS, dailyInterviewList);
    }
}
