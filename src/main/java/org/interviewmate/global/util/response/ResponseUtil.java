package org.interviewmate.global.util.response;

import org.interviewmate.global.error.dto.ErrorResponseDto;
import org.interviewmate.global.util.response.dto.ResponseDto;

import java.util.ArrayList;

public class ResponseUtil {

    /**
     *
     * @param code
     * @param data
     * @return status, code(message), data
     * @param <T>
     */
    public static <T> ResponseDto<T> SUCCESS(ResponseCode code, T data) {
        return new ResponseDto(ResponseStatus.SUCCESS, null, code.getMessage(), data);
    }

    /**
     *
     * @param code
     * @return status, code(code, message)
     * @param <T>
     */
    public static <T> ResponseDto<T> FAILURE(ResponseCode code) {
        return new ResponseDto(ResponseStatus.FAILURE, code.getCode(),code.getMessage(), null);
    }

    /**
     *
     * @param dto
     * @return status, error response
     * @param <T>
     */
    public static <T> ResponseDto<T> ERROR(ErrorResponseDto dto) {
        return new ResponseDto(ResponseStatus.ERROR,null,null, dto);
    }
}
