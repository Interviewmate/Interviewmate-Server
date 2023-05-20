package org.interviewmate.domain.analysis.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class AnalysisException extends CustomException {
    public AnalysisException(ErrorCode errorCode) {
        super(errorCode);
    }

}
