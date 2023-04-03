package org.interviewmate.global.error;

import lombok.extern.slf4j.Slf4j;
import org.interviewmate.global.error.dto.ErrorResponseDto;
import org.interviewmate.global.error.exception.CustomException;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseDto<ErrorResponseDto> customExceptionHandler(CustomException e) {
        ErrorResponseDto dto = ErrorResponseDto.of();
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());
        return ResponseUtil.ERROR(e.getErrorCode(), dto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseDto<ErrorResponseDto> handleValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        StringBuilder description = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            description.append("[");
            description.append(fieldError.getField());
            description.append("](은)는 ");
            description.append(fieldError.getDefaultMessage());
            description.append(" 입력된 값: [");
            description.append(fieldError.getRejectedValue());
            description.append("]\n");
        }

        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, ErrorResponseDto.of(description.toString()));

    }

    @ExceptionHandler({IllegalAccessException.class})
    protected ResponseDto<ErrorResponseDto> handleIllegalAccessException(IllegalAccessException e){
        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, ErrorResponseDto.of(e.getMessage()));
    }

    @ExceptionHandler({ConversionFailedException.class})
    protected ResponseDto<ErrorResponseDto> handleConversionFailedException(ConversionFailedException e) {
        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, ErrorResponseDto.of(e.getMessage()));
    }
}
