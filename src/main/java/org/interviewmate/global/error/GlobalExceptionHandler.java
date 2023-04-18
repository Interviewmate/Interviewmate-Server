package org.interviewmate.global.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.global.error.dto.ErrorResponseDto;
import org.interviewmate.global.error.exception.CustomException;
import org.interviewmate.global.util.response.ResponseUtil;
import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, String> descriptions = new HashMap<>();

    @ExceptionHandler
    protected ResponseDto<ErrorResponseDto> customExceptionHandler(CustomException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());
        return ResponseUtil.ERROR(e.getErrorCode(), dto);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseDto<ErrorResponseDto> handleValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                descriptions.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                descriptions.put( error.getObjectName(), error.getDefaultMessage());
            }
        }

        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, dto);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseDto<ErrorResponseDto> handleValidatedException(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        constraintViolations.stream()
                .forEach(constraintViolation -> {
                    descriptions.put("입력된 값 [" + constraintViolation.getInvalidValue() + "]", constraintViolation.getMessage());
                });

        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return ResponseUtil.ERROR(ErrorCode. BAD_REQUEST, dto);

    }

    @ExceptionHandler({IllegalAccessException.class})
    protected ResponseDto<ErrorResponseDto> handleIllegalAccessException(IllegalAccessException e){

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, dto);

    }

    @ExceptionHandler({ConversionFailedException.class})
    protected ResponseDto<ErrorResponseDto> handleConversionFailedException(ConversionFailedException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, dto);

    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseDto<ErrorResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, dto);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseDto<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return ResponseUtil.ERROR(ErrorCode.BAD_REQUEST, dto);

    }

}