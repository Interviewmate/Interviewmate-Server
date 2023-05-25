package org.interviewmate.domain.answer.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class AnswerException extends CustomException {
    public AnswerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
