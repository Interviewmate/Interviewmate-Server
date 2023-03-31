package org.interviewmate.debug;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class TestException extends CustomException {

    public TestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
