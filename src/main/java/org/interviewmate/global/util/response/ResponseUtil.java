package org.interviewmate.global.util.response;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.dto.ErrorResponseDto;
import org.interviewmate.global.util.response.dto.ResponseDto;

import java.util.ArrayList;

public class ResponseUtil {
    public static <T> ResponseDto<T> SUCCESS(ResponseCode code, T data) {
        return new ResponseDto(ResponseStatus.SUCCESS, code.name(), code.getMessage(), data);
    }

    public static <T> ResponseDto<T> FAILURE(ResponseCode code) {
        return new ResponseDto(ResponseStatus.FAILURE, code.name(),code.getMessage(), new ArrayList<>());
    }

    public static <T> ResponseDto<T> ERROR(ErrorCode code, ErrorResponseDto dto) {
        return new ResponseDto(ResponseStatus.ERROR,code.name(),code.getMessage(), dto);
    }
}
