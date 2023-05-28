package org.interviewmate.domain.interview.api;

import static org.interviewmate.global.util.response.ResponseCode.CREATED;
import static org.interviewmate.global.util.response.ResponseCode.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.interview.model.dto.response.InterviewVideoFindOutDto;
import org.interviewmate.domain.interview.service.InterviewVideoService;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Interview Video API", description = "면접 영상 관련 API")
@RestController
@RequestMapping("/interview_videos")
@RequiredArgsConstructor
public class InterviewVideoController {

    private final InterviewVideoService interviewVideoService;

    @Operation(summary = "면접 비디오 URL 등록", description = "S3에 업로드 한 면접 비디오에 대한 주소를 등록")
    @PostMapping("/interview/{interviewId}/question/{questionId}")
    public ResponseEntity<ResponseDto<String>> createInterviewVideo(
           @PathVariable Long interviewId,
           @PathVariable Long questionId,
           @RequestParam String url
    ) {
        interviewVideoService.createInterviewVideo(interviewId, questionId, url);
        return ResponseUtil.SUCCESS(CREATED, "등록 완료");
    }

    @Operation(summary = "면접 비디오 조회", description = "해당 면접에 해당하는 영상 조회")
    @GetMapping ("/interview/{interviewId}")
    public ResponseEntity<ResponseDto<InterviewVideoFindOutDto>> findInterviewVideo(@PathVariable Long interviewId) {
        InterviewVideoFindOutDto response = interviewVideoService.findInterviewVideo(interviewId);
        return ResponseUtil.SUCCESS(SUCCESS, response);
    }
}
