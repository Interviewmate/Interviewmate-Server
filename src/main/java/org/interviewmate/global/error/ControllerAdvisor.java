package org.interviewmate.global.error;

import lombok.extern.slf4j.Slf4j;
import org.interviewmate.global.error.dto.ErrorResponseDto;
import org.interviewmate.global.error.exception.CustomException;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler
    protected ResponseDto<ErrorResponseDto> customExceptionHandler(CustomException e) {
        ErrorResponseDto dto = ErrorResponseDto.of(e.getErrorCode());
        dto.setDetailMessage(e.getMessage());
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());
        return ResponseUtil.ERROR(dto);
    }
}
