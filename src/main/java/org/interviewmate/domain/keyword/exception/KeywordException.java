package org.interviewmate.domain.keyword.exception;

import lombok.Getter;
import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;
@Getter
public class KeywordException extends CustomException {

    public KeywordException(ErrorCode errorCode) {
        super(errorCode);
    }

}
