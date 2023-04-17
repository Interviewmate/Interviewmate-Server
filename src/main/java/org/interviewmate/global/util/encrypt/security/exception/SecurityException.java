package org.interviewmate.global.util.encrypt.security.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class SecurityException extends CustomException {

    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }

}
