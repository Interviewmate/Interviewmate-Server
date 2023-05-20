package org.interviewmate.domain.analysis.exception;

import org.interviewmate.global.error.ErrorCode;
import org.interviewmate.global.error.exception.CustomException;

public class GazeAnalysisException extends CustomException {
    public GazeAnalysisException(ErrorCode errorCode) {
        super(errorCode);
    }

}
