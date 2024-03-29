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
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    protected ResponseEntity customExceptionHandler(CustomException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);
        dto.setMessage(e.getErrorCode().getMessage());
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return new ResponseEntity(dto, e.getErrorCode().getCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity handleValidException(MethodArgumentNotValidException e) {

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

        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity handleValidatedException(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        constraintViolations.stream()
                .forEach(constraintViolation -> {
                    descriptions.put("입력된 값 [" + constraintViolation.getInvalidValue() + "]", constraintViolation.getMessage());
                });

        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalAccessException.class})
    protected ResponseEntity handleIllegalAccessException(IllegalAccessException e){

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConversionFailedException.class})
    protected ResponseEntity handleConversionFailedException(ConversionFailedException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        descriptions.put(e.toString(), e.getMessage());
        ErrorResponseDto dto = ErrorResponseDto.of(descriptions);

        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());

        return new ResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

}