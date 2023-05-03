package org.interviewmate.global.util.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.global.util.response.ResponseStatus;

@Getter
@Builder
public class ResponseDto<T> {

    private final ResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

}