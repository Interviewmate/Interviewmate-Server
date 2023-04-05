package org.interviewmate.domain.interview.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class InterviewException extends CustomException {
    public InterviewException(ErrorCode errorCode) {
        super(errorCode);
    }
}
