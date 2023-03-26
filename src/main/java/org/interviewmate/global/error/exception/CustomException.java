package org.interviewmate.global.error.exception;

import lombok.Getter;
import org.interviewmate.global.error.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
