package org.interviewmate.domain.question.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class QuestionException extends CustomException {
    public QuestionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
