package org.interviewmate.global.util.response;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.dto.ErrorResponseDto;
import org.interviewmate.global.util.response.dto.ResponseDto;

public class ResponseUtil {
    public static <T> ResponseDto<T> SUCCESS(ResponseCode code, T data) {
        return new ResponseDto(ResponseStatus.SUCCESS, code.getCode(), code.getMessage(), data);
    }

    public static <T> ResponseDto<T> FAILURE(ResponseCode code, T data) {
        return new ResponseDto(ResponseStatus.FAILURE, code.getCode(),code.getMessage(), data);
    }

    public static <T> ResponseDto<T> ERROR(ErrorCode code, ErrorResponseDto dto) {
        return new ResponseDto(ResponseStatus.ERROR,code.getCode(),code.getMessage(), dto);
    }
}
