package org.interviewmate.global.util.response;

import static org.interviewmate.global.util.response.ResponseStatus.FAILURE;
import static org.interviewmate.global.util.response.ResponseStatus.SUCCESS;

import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ResponseDto<T>> SUCCESS(ResponseCode code, T data) {
        return new ResponseEntity(
                ResponseDto.builder()
                        .status(SUCCESS)
                        .message(code.getMessage())
                        .result(data)
                        .build()
                , code.getCode());
    }

    public static <T> ResponseEntity<ResponseDto<T>> FAILURE(ResponseCode code, T data) {
        return new ResponseEntity(
                ResponseDto.builder()
                        .status(FAILURE)
                        .message(code.getMessage())
                        .result(data)
                        .build()
                , code.getCode());
    }

}
