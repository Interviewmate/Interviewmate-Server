package org.interviewmate.domain.interview.exception;

import lombok.Getter;
import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

@Getter
public class InterviewVideoException extends CustomException {

    public InterviewVideoException(ErrorCode errorCode) {
        super(errorCode);
    }

}
