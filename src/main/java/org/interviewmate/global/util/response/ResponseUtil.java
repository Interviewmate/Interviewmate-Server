package org.interviewmate.global.util.response;

import org.interviewmate.global.util.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T>ResponseEntity<ResponseDto<T>> SUCCESS(ResponseCode code, T data) {
        return new ResponseEntity(new ResponseDto(ResponseStatus.SUCCESS, code.getMessage(), data), code.getCode());
    }

    public static <T>ResponseEntity<ResponseDto<T>> FAILURE(ResponseCode code, T data) {
        return new ResponseEntity(new ResponseDto(ResponseStatus.FAILURE, code.getMessage(), data), code.getCode());
    }
}
